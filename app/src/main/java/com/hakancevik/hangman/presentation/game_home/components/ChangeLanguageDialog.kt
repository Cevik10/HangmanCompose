package com.hakancevik.hangman.presentation.game_home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChangeLanguageDialog(
    onLanguageSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedLanguage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedLanguage?.let { onLanguageSelected(it) }
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        },
        title = {
            Text(text = "Change Language")
        },
        text = {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                LanguageOption("English", "en", selectedLanguage) {
                    selectedLanguage = it
                }
                LanguageOption("Español", "es", selectedLanguage) {
                    selectedLanguage = it
                }
                LanguageOption("Turkish", "tr", selectedLanguage) {
                    selectedLanguage = it
                }
                LanguageOption("Deutsch", "de", selectedLanguage) {
                    selectedLanguage = it
                }
                LanguageOption("Arabic", "ar", selectedLanguage) {
                    selectedLanguage = it
                }
            }
        }
    )
}

@Composable
fun LanguageOption(
    language: String,
    languageCode: String,
    selectedLanguage: String?,
    onLanguageSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = (selectedLanguage == languageCode),
                onClick = {
                    onLanguageSelected(languageCode)
                }
            )
            .padding(8.dp)
    ) {
        RadioButton(
            selected = (selectedLanguage == languageCode),
            onClick = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = language)
    }
}
