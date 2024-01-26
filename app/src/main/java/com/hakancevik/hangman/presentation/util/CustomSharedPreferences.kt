package com.hakancevik.hangman.presentation.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class CustomSharedPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        private const val SELECTED_LANGUAGE_KEY = "selectedLanguage"
    }

    fun saveSelectedLanguage(language: String) {
        sharedPreferences.edit().putString(SELECTED_LANGUAGE_KEY, language).apply()
    }

    fun getSelectedLanguage(): String? {
        return sharedPreferences.getString(SELECTED_LANGUAGE_KEY, null)
    }
}