package com.hakancevik.hangman.presentation

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hakancevik.hangman.R
import com.hakancevik.hangman.presentation.game_home.GameScreen
import com.hakancevik.hangman.presentation.select_language.SelectLanguageScreen
import com.hakancevik.hangman.presentation.select_language.components.LanguageRadioButton
import com.hakancevik.hangman.presentation.util.Screen
import com.hakancevik.hangman.ui.theme.HangmanTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HangmanTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SelectLanguageScreen.route
                    ) {
                        composable(route = Screen.SelectLanguageScreen.route) {
                            SelectLanguageScreen(navController = navController)
                        }
                        composable(
                            route = Screen.GameScreen.route +
                                    "?selectedLanguage={selectedLanguage}",
                            arguments = listOf(
                                navArgument(
                                    name = "selectedLanguage"
                                ) {
                                    type = NavType.StringType
                                    defaultValue = "en"
                                }
                            )
                        ) {
                            val selectedLanguage = it.arguments?.getString("selectedLanguage") ?: "en"
                            GameScreen(
                                navController = navController,
                                selectedLanguage = selectedLanguage,
                                onPopBack = { false },
                                onNavigateUp = {}
                            )


                            SetLocale(selectedLanguage,this@MainActivity)





                        }
                    }
                }
            }
        }
    }
}


fun SetLocale(languageCode: String, context: Context) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val resources = context.resources
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}

@Composable
fun LanguageButton(letter: Char, onLetterClick: (Char) -> Unit) {
    Button(
        onClick = { onLetterClick(letter) },

        shape = CircleShape,
        modifier = Modifier
            .padding(4.dp)
            .size(48.dp)
    ) {
        Text(
            text = "asa",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Red
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HangmanTheme {

    }
}



