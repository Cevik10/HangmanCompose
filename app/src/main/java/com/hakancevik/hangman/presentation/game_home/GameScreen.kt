package com.hakancevik.hangman.presentation.game_home

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hakancevik.hangman.R
import com.hakancevik.hangman.presentation.game_home.components.HangmanBody
import com.hakancevik.hangman.ui.theme.HangmanTheme
import java.util.Locale

@Composable
fun GameScreen(
    navController: NavController,
    selectedLanguage: String,
) {
    val context = LocalContext.current

    changeLanguage(context, selectedLanguage)

    var lives by remember { mutableIntStateOf(6) }

    var selectedLetters by remember { mutableStateOf<Set<Char>>(emptySet()) }
    val words = remember { context.resources.getStringArray(R.array.hangman_words).toList() }
    val selectedWord by remember { mutableStateOf(words.random()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {


        Box(
            modifier = Modifier
                .height(265.dp)
                .fillMaxWidth()
        ) {
            HangmanBody(lives = lives)
        }


        HangmanWordDisplay(selectedWord, selectedLetters)

        AlphabetMenu(
            context = context,
            onLetterSelected = { selectedLetter ->
                selectedLetters = selectedLetters + selectedLetter[0]

                if (!selectedWord.contains(selectedLetter[0])) {

                    lives -= 1

                    Toast.makeText(context, "Incorrect letter! Lives left: $lives", Toast.LENGTH_LONG).show()

                    // Check if lives are exhausted
                    if (lives == 0) {

                    }
                }
            },
            guessedLetters = selectedLetters
        )
    }

    if (lives == 0) {
        ShowGameOverDialog(onRetryClick = {
            // Reset the game or navigate to a new screen
            selectedLetters = emptySet()
            lives = 6
        })
    }


}

@Composable
fun AlphabetMenu(context: Context, maxLettersPerRow: Int = 8, onLetterSelected: (String) -> Unit, guessedLetters: Set<Char>) {
    var selectedLetters by remember { mutableStateOf<List<String>>(emptyList()) }

    val alphabetArray = context.resources.getStringArray(R.array.alphabet_array).toList()

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(maxLettersPerRow),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(alphabetArray) { letter ->
                AlphabetLetter(
                    letter = letter,
                    isSelected = selectedLetters.contains(letter),
                    onLetterClick = {
                        if (selectedLetters.contains(letter)) {
                            selectedLetters = selectedLetters - letter
                        } else {
                            selectedLetters = selectedLetters + letter
                        }
                        onLetterSelected(letter)
                    }
                )
            }
        }
    }
}

/*@Composable
fun AlphabetLetter(
    letter: String,
    isSelected: Boolean,
    onLetterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(color = Color.Red,
                shape = RoundedCornerShape(
                    topStart = 13.dp,
                    topEnd = 13.dp,
                    bottomEnd = 13.dp,
                    bottomStart = 13.dp
                ))
            .padding(6.dp)

            .clickable {
                if (!isSelected) {
                    onLetterClick()
                }
            }
            .background(
                color = if (isSelected) Color.Gray else Color.Green,
                shape = RoundedCornerShape(
                    topStart = 13.dp,
                    topEnd = 13.dp,
                    bottomEnd = 13.dp,
                    bottomStart = 13.dp
                )
            )
    ) {
        Text(
            text = letter,
            color = if (isSelected) Color.White else Color.Black,
            modifier = Modifier.padding(8.dp)
        )
    }
}*/

@Composable
fun AlphabetLetter(
    letter: String,
    isSelected: Boolean,
    onLetterClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable {
                if (!isSelected) {
                    onLetterClick()
                }
            }
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color.Gray else Yellow),
        border = BorderStroke(width = 2.dp, Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )


    ) {
        Text(
            text = letter,
            color = if (isSelected) Color.White else Color.Black,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun HangmanWordDisplay(word: String, guessedLetters: Set<Char>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        for (letter in word) {
            val displayLetter = if (guessedLetters.contains(letter) || letter.isWhitespace()) letter else '_'
            Text(
                text = displayLetter.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AlphabetScreenPreviewDark() {
    HangmanTheme {

    }
}

@Preview(showBackground = true)
@Composable
fun AlphabetScreenPreviewLight() {
    HangmanTheme(darkTheme = false) {
        ShowGameWonDialog()
    }
}


private fun changeLanguage(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)

    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
}

// Function to show the Game Over dialog
@Composable
fun ShowGameOverDialog(onRetryClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            // Handle dismiss request if needed
        },
        title = {
            Text(text = "Game Over")
        },
        text = {
            Text(text = "You ran out of lives. Better luck next time!")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // Call the provided onRetryClick function
                    onRetryClick()
                }
            ) {
                Text(text = "Retry")
            }
        }
    )
}

// Function to show the Game Won dialog
@Composable
fun ShowGameWonDialog() {
    AlertDialog(
        onDismissRequest = {
            // Handle dismiss request if needed
        },
        title = {
            Text(text = "Congratulations!")
        },
        text = {
            Text(text = "You guessed the word correctly!")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // Reset the game or navigate to a new screen

                }
            ) {
                Text(text = "Next")
            }
        }
    )
}







