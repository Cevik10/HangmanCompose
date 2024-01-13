package com.hakancevik.hangman.presentation.game_home

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hakancevik.hangman.R
import com.hakancevik.hangman.presentation.LanguageButton
import com.hakancevik.hangman.ui.theme.HangmanTheme
import java.util.Locale

@Composable
fun GameScreen(
    navController: NavController,
    selectedLanguage: String,
) {
    val context = LocalContext.current

    changeLanguage(context, selectedLanguage)

    var attempts by remember { mutableStateOf(6) }
    var isGameOver by remember { mutableStateOf(false) }

    var letterGuess by remember { mutableStateOf("") }


    var selectedLetters by remember { mutableStateOf<Set<Char>>(emptySet()) }
    val words = remember { context.resources.getStringArray(R.array.hangman_words).toList() }
    val selectedWord by remember { mutableStateOf(words.random()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        HangmanWordDisplay(selectedWord, selectedLetters)

        AlphabetMenu(
            context = context,
            onLetterSelected = { selectedLetter ->
                selectedLetters = selectedLetters + selectedLetter[0]

                if (!selectedWord.contains(selectedLetter[0])) {

                    Toast.makeText(context, "harf yok", Toast.LENGTH_LONG).show()
                }
            },
            guessedLetters = selectedLetters
        )
    }


}

@Composable
fun AlphabetMenu(context: Context, maxLettersPerRow: Int = 6, onLetterSelected: (String) -> Unit, guessedLetters: Set<Char>) {
    var selectedLetters by remember { mutableStateOf<List<String>>(emptyList()) }

    val alphabetArray = context.resources.getStringArray(R.array.alphabet_array).toList()

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(maxLettersPerRow),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(alphabetArray) { letter ->
                AlphabetLetter(
                    letter = letter,
                    isSelected = selectedLetters.contains(letter),
                    onLetterClick = {
                        if (selectedLetters.contains(letter)) {
                            selectedLetters = selectedLetters - letter
                        } else {
                            selectedLetters = selectedLetters + letter
                        }
                        onLetterSelected(letter)
                    }
                )
            }
        }
    }
}

@Composable
fun AlphabetLetter(
    letter: String,
    isSelected: Boolean,
    onLetterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                if (!isSelected) {
                    onLetterClick()
                }
            }
            .background(if (isSelected) Color.Gray else Color.Green)
    ) {
        Text(
            text = letter,
            color = if (isSelected) Color.White else Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun HangmanWordDisplay(word: String, guessedLetters: Set<Char>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        for (letter in word) {
            val displayLetter = if (guessedLetters.contains(letter) || letter.isWhitespace()) letter else '_'
            Text(
                text = displayLetter.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AlphabetScreenPreviewDark() {
    HangmanTheme {

    }
}

@Preview(showBackground = true)
@Composable
fun AlphabetScreenPreviewLight() {
    HangmanTheme(darkTheme = false) {

    }
}



private fun changeLanguage(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)

    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
}





