package com.hakancevik.hangman.presentation.select_language

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SelectLanguageViewModel @Inject constructor() : ViewModel() {

    private val _selectedLanguage = MutableStateFlow<String?>("en")
    val selectedLanguage: StateFlow<String?> = _selectedLanguage.asStateFlow()

    fun setSelectedLanguage(language: String) {
        _selectedLanguage.value = language
    }


}