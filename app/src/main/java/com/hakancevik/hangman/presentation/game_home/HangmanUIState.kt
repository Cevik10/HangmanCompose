package com.hakancevik.hangman.presentation.game_home

data class HangmanUIState(
    val selectedWord: String = "hangman", // Replace with your logic to get a random word
    val selectedLetters: Set<Char> = emptySet(),
    val lives: Int = 6,
    val gameScoreState: GameScoreState = GameScoreState.StillPlaying
)
