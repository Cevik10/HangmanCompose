package com.hakancevik.hangman.presentation.util

sealed class Screen(val route: String) {
    object GameScreen : Screen("game_screen")
    object SelectLanguageScreen : Screen("select_language_screen")
}
