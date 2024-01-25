package com.hakancevik.hangman.presentation.game_home

data class GameUiState(
    val wordRandomlyChosen: String? = "",
    val categoryRandomlyChosen: String = "",
    val usedLetters: Set<Char> = emptySet(),
    val correctLetters: Set<Char> = emptySet(),
    val wrongLetters: Set<Char> = emptySet(),
    val livesLeft: Int = 6,
    val isGameOver: Boolean = false,
    val streakCount: Int = 0,
)