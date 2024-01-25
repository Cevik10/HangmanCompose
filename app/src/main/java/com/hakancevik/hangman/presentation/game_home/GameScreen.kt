package com.hakancevik.hangman.presentation.game_home


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hakancevik.hangman.R
import com.hakancevik.hangman.presentation.MainActivity
import com.hakancevik.hangman.presentation.game_home.components.GameOverDialog
import com.hakancevik.hangman.presentation.game_home.components.HangmanBody
import com.hakancevik.hangman.ui.theme.HangmanTheme
import java.text.Normalizer
import java.util.Locale


@Composable
fun GameScreen(
    navController: NavController,
    selectedLanguage: String,
    onNavigateUp: () -> Unit,
    onPopBack: () -> Boolean,
    viewModel: GameViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    setLanguage(context,selectedLanguage)
    DisposableEffect(selectedLanguage) {
        viewModel.changeLanguage(selectedLanguage)
        onDispose { /* Dispose logic, if needed */ }
    }

    val gameUiState by viewModel.uiState.collectAsState()

    GameContent(
        onPopBack = onPopBack,
        onNavigateUp = onNavigateUp,
        wordChosen = gameUiState.wordRandomlyChosen,
        correctLetters = gameUiState.correctLetters,
        livesCount = gameUiState.livesLeft,
        checkUserGuess = { viewModel.checkUserGuess(it) },
        resetGame = { viewModel.resetStates() },
        isGameOver = viewModel.isGameOver,
        usedLetters = gameUiState.usedLetters,
        category = gameUiState.categoryRandomlyChosen,
        winCount = gameUiState.streakCount,
        isWordCorrectlyGuessed = {
            viewModel.isWordCorrectlyGuessed(
                gameUiState.wordRandomlyChosen,
                gameUiState.correctLetters
            )
        }
    )


}

@Composable
private fun GameContent(
    wordChosen: String?,
    correctLetters: Set<Char>,
    livesCount: Int,
    checkUserGuess: (Char) -> Unit,
    resetGame: () -> Unit,
    isGameOver: Boolean,
    usedLetters: Set<Char>,
    category: String,
    winCount: Int,
    onNavigateUp: () -> Unit,
    isWordCorrectlyGuessed: () -> Boolean,
    onPopBack: () -> Boolean
) {


    if (isWordCorrectlyGuessed()) {
        resetGame()
    }
    if (isGameOver) {
        GameOverDialog(
            resetGame = resetGame,
            wordChosen = wordChosen,
            hitsCount = winCount
        )
    }
    //BackToHomeDialog(onNavigateUp)
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {

        LivesLeftRow(
            livesCount
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            ChosenWordRow(
                wordChosen,
                correctLetters
            )
            TipAndCountTextRow(tip = category, winCount)


            val alphabetArrayRes = R.array.alphabet_array
            val alphabetArray: Array<String> = LocalContext.current.resources.getStringArray(alphabetArrayRes)
            val alphabetList: List<Char> = alphabetArray.flatMap { it.toList() }

            KeyboardLayout(
                alphabetList = alphabetList,
                checkUserGuess = { checkUserGuess(it) },
                correctLetters = correctLetters,
                usedLetters = usedLetters,
            )
        }

        HangmanBody(lives = livesCount)
    }
}


@Composable
fun materialColor(): Color {
    return MaterialTheme.colorScheme.onSurface
}


@Composable
private fun ChosenWordRow(
    wordChosen: String?,
    correctLetters: Set<Char>,
) {

    val materialColor = remember { mutableStateOf(Color.Transparent) }
    materialColor.value = materialColor()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        LazyRow {
            items(wordChosen!!.length) { letterIndex ->
                val currentChar = wordChosen[letterIndex]
                if (currentChar.isWhitespace()) {
                    Spacer(modifier = Modifier.padding(16.dp))

                } else if (currentChar == '-') {
                    Text(
                        modifier = Modifier
                            .padding(1.2.dp)
                            .size(32.dp),
                        text = "-",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    WordLetter(
                        letter = currentChar,
                        correctLetters = correctLetters
                    )
                }
            }
        }
    }
}


@Composable
@Stable
private fun WordLetter(
    modifier: Modifier = Modifier,
    letter: Char,
    correctLetters: Set<Char> = emptySet()
) {

    val letterNormalized = Normalizer.normalize(letter.toString(), Normalizer.Form.NFD)
    val normalizedChar: Char = letterNormalized[0]
    val isLetterCorrect: Boolean = correctLetters.contains(normalizedChar)
    val alphaValue = if (isLetterCorrect) ALPHA_CORRECT_VALUE else ALPHA_INCORRECT_VALUE
    val alphaAnimation: Float by animateFloatAsState(
        targetValue = alphaValue,
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing), label = ""
    )

    val haveAnimationOrNot = if (alphaValue == ALPHA_CORRECT_VALUE) alphaAnimation else alphaValue

    Card(
        modifier = modifier
            .padding(2.4.dp)
            .size(32.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            contentColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .alpha(haveAnimationOrNot),
            text = letter.toString().uppercase(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }
}


@Composable
fun TipAndCountTextRow(tip: String, winCount: Int) {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = stringResource(id = R.string.TIP_TEXT, tip))
        AnimatedWinCount(winCount = winCount)
    }
}

@Composable
private fun AnimatedWinCount(winCount: Int) {
    AnimatedContent(
        targetState = winCount,
        transitionSpec = {
            if (targetState > initialState) {
                (slideInVertically { height -> height } + fadeIn()).togetherWith(slideOutVertically { height -> -height } + fadeOut())
            } else {
                (slideInVertically { height -> -height } + fadeIn()).togetherWith(slideOutVertically { height -> height } + fadeOut())
            }.using(
                SizeTransform(clip = false)
            )
        }, label = ""
    ) { targetCount ->
        Text(text = stringResource(id = R.string.WIN_COUNT_TEXT, targetCount))
    }
}

private const val ALPHA_CORRECT_VALUE = 1F
private const val ALPHA_INCORRECT_VALUE = 0F


@Composable
private fun LivesLeftRow(
    livesCount: Int
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val pulsate by infiniteTransition.animateFloat(
        initialValue = 35f,
        targetValue = 37f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 4000
                35f at 0 with LinearOutSlowInEasing
                37f at 200 with LinearOutSlowInEasing
                35f at 400 with LinearOutSlowInEasing
                37f at 600 with LinearOutSlowInEasing
                35f at 800 with LinearOutSlowInEasing
                35f at 4000 with LinearEasing
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "",

        )
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .requiredHeight(38.dp)
            .fillMaxWidth()
    ) {
        repeat(livesCount) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Lives Player Count: $livesCount",
                tint = Color.Red,
                modifier = Modifier
                    .size(pulsate.dp)
            )
        }
    }
}


@Composable
private fun KeyboardLayout(
    checkUserGuess: (Char) -> Unit,
    alphabetList: List<Char>,
    correctLetters: Set<Char>,
    usedLetters: Set<Char>,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(40.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(alphabetList.size) {
            val keyLetter = alphabetList[it]
            key(it) {
                KeyboardKey(
                    letterFromButton = keyLetter,
                    correctLetters = correctLetters,
                    usedLetters = usedLetters,
                    checkUserGuess = { checkUserGuess(keyLetter) }
                )
            }
        }
    }
}


@Composable
fun IconRow(
    modifier: Modifier = Modifier,
    orientationInsideRow: Arrangement.Horizontal,
    onClick: () -> Unit,
    imageVector: ImageVector
) {
    Row(
        modifier = modifier,
        horizontalArrangement = orientationInsideRow
    ) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun TopAppBarRow(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onPopBack: () -> Boolean
) {
    var showActionFromBackIcon by rememberSaveable {
        mutableStateOf(false)
    }
    var showActionFromInfoIcon by rememberSaveable {
        mutableStateOf(false)
    }

//    if (showActionFromInfoIcon) {
//        TipDialog {
//            showActionFromInfoIcon = false
//        }
//    }


    Row(modifier = modifier.fillMaxWidth()) {
        val modifierWeightValue = modifier
            .weight(0.5f)
        IconRow(
            modifier = modifierWeightValue,
            orientationInsideRow = Arrangement.Start,
            onClick = { onPopBack() },
            imageVector = Icons.Rounded.ArrowBack,
        )
        IconRow(
            modifier = modifierWeightValue,
            orientationInsideRow = Arrangement.End,
            onClick = { showActionFromInfoIcon = true },
            imageVector = Icons.Rounded.Info,

            )
    }
}

@Preview(showBackground = true)
@Composable
fun BackIconButtonPreview() {
    HangmanTheme {
        TopAppBarRow(onNavigateUp = {}) { true }
    }
}


enum class ButtonState { Correct, Incorrect }

private class ButtonTransitionData(
    color: State<Color>
) {
    val color by color
}

@Composable
private fun updateButtonTransitionData(
    checkCorrectness: Boolean
): ButtonTransitionData {

    val targetState = when (checkCorrectness) {
        true -> ButtonState.Correct
        false -> ButtonState.Incorrect
    }
    val transition = updateTransition(
        targetState = targetState,
        label = "button state"
    )
    val color = transition.animateColor(
        label = "color"
    ) { state ->
        when (state) {
            ButtonState.Correct -> MaterialTheme.colorScheme.secondary
            ButtonState.Incorrect -> MaterialTheme.colorScheme.error
        }
    }
    return remember(transition) { ButtonTransitionData(color) }
}

@Composable
private fun KeyboardKey(
    modifier: Modifier = Modifier,
    letterFromButton: Char,
    correctLetters: Set<Char>,
    usedLetters: Set<Char>,
    checkUserGuess: (Char) -> Unit,
) {
    val isEnabled = remember(
        letterFromButton,
        usedLetters
    ) { !usedLetters.contains(letterFromButton) }
    val checkCorrectness = remember(
        letterFromButton,
        correctLetters
    ) { correctLetters.contains(letterFromButton.lowercaseChar()) }

    val transitionData = updateButtonTransitionData(checkCorrectness = checkCorrectness)

    TextButton(
        onClick = {
            checkUserGuess(letterFromButton)
        },
        enabled = isEnabled,
        shape = ShapeDefaults.ExtraLarge,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = transitionData.color,
            disabledContentColor = MaterialTheme.colorScheme.outline
        ),
        modifier = modifier
            .padding(4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text = letterFromButton.toString(),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
        )
    }
}


fun setLanguage(context: Context,languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)

    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
}

/*

@Preview(showBackground = true)
@Composable
fun MyPreview() {
    HangmanTheme {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "viewModel.lives.value.toString()")
            HangmanWordDisplay(word = "hakan cevik", guessedLetters = setOf('a', 'n', 'h'))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameContent(
    viewModel: GameViewModel,
    onRestartGame: () -> Unit
) {
    val context = LocalContext.current

    var selectedLetters by remember { mutableStateOf<Set<Char>>(emptySet()) }


//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Hangman Game") },
//                actions = {
//                    IconButton(onClick = { }) {
//                        Icon(
//                            painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
//                            contentDescription = "Menu"
//                        )
//                    }
//                }
//            )
//        },
//
//
//        content = { padding ->
//
//            Column(
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier
//                    .fillMaxSize()
//            ) {
//
//                Text(
//                    text = "letter",
//                    fontSize = 21.sp,
//                    color = Color.Black,
//                )
//                Text(
//                    text = "letter",
//                    fontSize = 21.sp,
//                    color = Color.Black,
//                    modifier = Modifier.padding(8.dp)
//                )
//
//
//
//            }
//        }
//    )
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

*/
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
}*//*




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


val alphabet = listOf(
    "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
    "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
    "ä", "ö", "ü", "ß"
)


@Composable
fun StartGameSection(onStartClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.AccountBox,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(96.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Start Game")
        }
    }
}

@Composable
fun HangmanWordSection(currentWordState: String, remainingAttempts: Int) {
    Column(
        modifier = Modifier.size(600.dp, 600.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentWordState,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Remaining Attempts: $remainingAttempts",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun KeyboardSection(guessedLetters: Set<Char>, onLetterClick: (Char) -> Unit) {
    val keyboardLetters = ('a'..'z').toList()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(keyboardLetters) { letter ->
            KeyboardButton(
                letter = letter,
                isEnabled = letter !in guessedLetters,
                onLetterClick = { onLetterClick(letter) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AlphabetButtonsPreview() {
    HangmanTheme {
        KeyboardSection(listOf<Char>('a', 's').toSet(), onLetterClick = {})
    }
}


@Composable
fun KeyboardButton(letter: Char, isEnabled: Boolean, onLetterClick: () -> Unit) {
    Button(
        onClick = { onLetterClick() },
        enabled = isEnabled,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Text(text = letter.toString(), color = Yellow)
    }
}

@Composable
fun AlphabetButton(letter: String, isSelected: Boolean, onLetterClick: () -> Unit) {
    Button(
        onClick = { onLetterClick() },
        enabled = !isSelected,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        colors = if (isSelected) ButtonDefaults.buttonColors(disabledContainerColor = Color.Gray) else ButtonDefaults.buttonColors()
    ) {
        Text(text = letter)
    }
}


@Composable
fun AlphabetButtons() {
    var selectedLetter by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        LazyColumn {
            itemsIndexed(items = alphabet) { index, letter ->
                AlphabetButton(
                    letter = letter,
                    isSelected = selectedLetter == letter,
                    onLetterClick = {
                        selectedLetter = if (selectedLetter == letter) null else letter
                    }
                )
            }
        }

        Text(
            text = "Selected Letter: ${selectedLetter ?: "None"}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun GameResultSection(onRestartClick: () -> Unit, isGameWon: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isGameWon) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(96.dp)
            )
        } else {
            Icon(
                painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(96.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onRestartClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Restart Game")
        }
    }
}


*/
