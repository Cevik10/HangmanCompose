package com.hakancevik.hangman.presentation.util

fun List<String>.contains(s: String, ignoreCase: Boolean = true): Boolean {

    return any { it.equals(s, ignoreCase) }
}
