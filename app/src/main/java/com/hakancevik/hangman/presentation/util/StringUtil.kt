package com.hakancevik.hangman.presentation.util

import java.text.Normalizer

object StringUtil {

    fun removeWhitespacesAndHyphens(word: String): String {
        return word.filterNot { it.isWhitespace() || it == '-' }
    }

    fun normalizeWord(word: String): String {
        val regex = "[^\\p{ASCII}]".toRegex()
        return Normalizer.normalize(word, Normalizer.Form.NFD).replace(regex, "")
    }
}