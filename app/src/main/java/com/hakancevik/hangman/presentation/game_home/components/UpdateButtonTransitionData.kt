package com.hakancevik.hangman.presentation.game_home.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.hakancevik.hangman.ui.theme.HangmanTheme

enum class ButtonState { Correct, Incorrect }

class ButtonTransitionData(
    color: State<Color>
) {
    val color by color
}

@Composable
fun UpdateButtonTransitionData(
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
            ButtonState.Correct -> Color(0xFFB2DBAE)
            ButtonState.Incorrect -> Color(0xFF757575)
        }
    }
    return remember(transition) { ButtonTransitionData(color) }
}