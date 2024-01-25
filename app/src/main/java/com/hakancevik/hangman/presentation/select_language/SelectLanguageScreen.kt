package com.hakancevik.hangman.presentation.select_language

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hakancevik.hangman.R
import com.hakancevik.hangman.presentation.select_language.components.LanguageRadioButton
import com.hakancevik.hangman.presentation.util.Screen
import com.hakancevik.hangman.ui.theme.HangmanTheme


@Composable
fun SelectLanguageScreen(
    navController: NavController,
) {
    var selectedLanguage by remember { mutableStateOf<String?>("en") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
                .padding(16.dp)
                .clip(MaterialTheme.shapes.medium)
                .border(2.dp, Color.White, MaterialTheme.shapes.medium)
        ) {
            LanguageRadioButton(
                language = "English",
                flagImageResId = R.drawable.usa,
                isSelected = selectedLanguage == "en"
            ) {
                selectedLanguage = "en"
            }
            LanguageRadioButton(
                language = "Español",
                flagImageResId = R.drawable.spain,
                isSelected = selectedLanguage == "es"
            ) {
                selectedLanguage = "es"
            }

            LanguageRadioButton(
                language = "Turkish",
                flagImageResId = R.drawable.turkey,
                isSelected = selectedLanguage == "tr"
            ) {
                selectedLanguage = "tr"
            }

            LanguageRadioButton(
                language = "Deutsch",
                flagImageResId = R.drawable.germany,
                isSelected = selectedLanguage == "de"
            ) {
                selectedLanguage = "de"
            }

            LanguageRadioButton(
                language = "Arabic",
                flagImageResId = R.drawable.arabic,
                isSelected = selectedLanguage == "ar"
            ) {
                selectedLanguage = "ar"
            }

            // Add more languages as needed
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                selectedLanguage?.let {
                    navController.navigate(
                        Screen.GameScreen.route +
                                "?selectedLanguage=${it}"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Start", fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HangmanTheme {

    }
}



