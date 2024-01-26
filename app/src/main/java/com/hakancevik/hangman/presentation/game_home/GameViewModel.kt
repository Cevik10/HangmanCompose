package com.hakancevik.hangman.presentation.game_home

import androidx.lifecycle.ViewModel
import com.hakancevik.hangman.data.HangmanDataSource
import com.hakancevik.hangman.presentation.util.StringUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.Collator
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val hangmanDataSource: HangmanDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState = _uiState.asStateFlow()

    private var lettersGuessed: MutableSet<Char> = mutableSetOf()
    private var correctLetters: MutableSet<Char> = mutableSetOf()
    private var wrongLetters: MutableSet<Char> = mutableSetOf()
    private var word: String? = ""
    private var _currentLetterGuessed: Char = ' '
    val isGameOver: Boolean get() = _uiState.value.livesLeft == 0


    private var _currentStreakCount: Int = 0

    private var _currentLanguageCode: String = Locale.getDefault().language

    init {
        pickRandomWordAndCategory()
    }

    fun getCurrentLanguageCode(): String {
        return _currentLanguageCode
    }

    fun changeLanguage(languageCode: String) {
        if (_currentLanguageCode != languageCode) {
            _currentLanguageCode = languageCode

            hangmanDataSource.setLanguage(languageCode)

            pickRandomWordAndCategory()
        }
    }

    fun pickRandomWordAndCategory() {
        val currentCategory = hangmanDataSource.getRandomCategory()
        val currentWord = hangmanDataSource.getRandomWordByCategoryAndLanguage(currentCategory, _currentLanguageCode)

        word = currentWord.lowercase()

        _uiState.update { currentState ->
            currentState.copy(
                wordRandomlyChosen = word,
                categoryRandomlyChosen = currentCategory
            )
        }
    }



    private fun isLetterGuessCorrect(letterFromButton: Char): Boolean? {
        val collator: Collator = Collator.getInstance()
        collator.strength = Collator.PRIMARY

        val wordRandomlyChosen = _uiState.value.wordRandomlyChosen
        val normalizedLetterFromButton = letterFromButton.toString()

        return wordRandomlyChosen?.any { char ->
            collator.compare(char.toString(), normalizedLetterFromButton) == 0
        }
    }

    fun checkUserGuess(letterFromButton: Char) {
        lettersGuessed.add(letterFromButton)
        _currentLetterGuessed = letterFromButton.lowercaseChar()

        if (isLetterGuessCorrect(letterFromButton) == true) {
            correctLetters.add(_currentLetterGuessed)
            _uiState.update { currentState ->
                currentState.copy(
                    usedLetters = lettersGuessed.toSet(),
                    correctLetters = correctLetters.toSet(),
                )
            }
        } else {
            wrongLetters.add(_currentLetterGuessed)

            _uiState.update { currentState ->
                currentState.copy(
                    usedLetters = lettersGuessed.toSet(),
                    wrongLetters = wrongLetters.toSet(),
                    livesLeft = currentState.livesLeft.dec(),
                )
            }
        }
    }

    fun isWordCorrectlyGuessed(wordChosen: String? = word, correctLetters: Set<Char>) =
        wordChosen?.run {
            val wordWithoutWhitespaces = StringUtil.removeWhitespacesAndHyphens(this)
            val normalizedWord = StringUtil.normalizeWord(wordWithoutWhitespaces)
            correctLetters.containsAll(normalizedWord.toList())
        } == true

    fun resetStates() {
        _currentStreakCount = increaseStreakCount()
        _uiState.value = GameUiState(streakCount = _currentStreakCount)
        lettersGuessed.clear()
        correctLetters.clear()
        wrongLetters.clear()
        _currentLetterGuessed = ' '
        pickRandomWordAndCategory()
    }

    private fun increaseStreakCount() = if (!isGameOver) ++_currentStreakCount else 0


    fun resetGame() {
        _currentStreakCount = 0
        _uiState.value = GameUiState(streakCount = _currentStreakCount)
        lettersGuessed.clear()
        correctLetters.clear()
        wrongLetters.clear()
        _currentLetterGuessed = ' '
    }


}
