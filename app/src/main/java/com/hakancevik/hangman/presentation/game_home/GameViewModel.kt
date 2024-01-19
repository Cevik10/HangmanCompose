package com.hakancevik.hangman.presentation.game_home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HangmanUIState())
    val uiState: StateFlow<HangmanUIState> = _uiState.asStateFlow()

    // Function to update UI state when a letter is selected
    fun onLetterSelected(selectedLetter: String) {
        // Implement the logic to check if the selected letter is correct or not
        // Update lives, selectedLetters, and check if the game is over
        // ...

        // When you update the state, remember to use _uiState.value = updatedState
    }

    // Function to handle retry
    fun onRetryClick() {
        // Reset the game or navigate to a new screen
        // ...

        // When you update the state, remember to use _uiState.value = updatedState
    }
}
