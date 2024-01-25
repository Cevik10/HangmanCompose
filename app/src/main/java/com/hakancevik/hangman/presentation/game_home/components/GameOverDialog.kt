package com.hakancevik.hangman.presentation.game_home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hakancevik.hangman.R

@Composable
fun GameOverDialog(
    resetGame: () -> Unit,
    wordChosen: String?,
    hitsCount: Int,
) {

    AlertDialog(
        icon = {
            Image(
                painter = painterResource(id = R.drawable.sad),
                contentDescription = null,
                modifier = Modifier
                    .size(62.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
        },
        title = { GameOverText() },
        text = {
            if (wordChosen != null) {
                DialogContentColumn(wordChosen = wordChosen, hitsCount)
            }
        },
        onDismissRequest = { resetGame() },
        confirmButton = {
            Button(
                onClick = { resetGame() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = stringResource(id = R.string.PLAY_AGAIN_BUTTON_DIALOG))
            }
        }
    )
}

@Composable
private fun GameOverText() {
    Text(
        text = stringResource(id = R.string.GAME_OVER_TEXT_DIALOG),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun DialogContentColumn(
    wordChosen: String,
    hitsCount: Int
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        RevealedWordRow(wordChosen)
        Divider(
            modifier = Modifier
                .alpha(0.36f)
                .fillMaxWidth(0.5f)
                .padding(vertical = 12.dp),
            color = MaterialTheme.colorScheme.primary
        )
        ShowHowManyHitsUserGot(hitsCount = hitsCount)
    }
}


@Composable
private fun RevealedWordRow(
    wordChosen: String,
) {
    Row {
        Text(
            text = stringResource(
                id = R.string.REVEAL_WORD_TEXT_DIALOG,
                wordChosen
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ShowHowManyHitsUserGot(hitsCount: Int) {
    Row(
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = stringResource(
                id = R.string.SHOW_HOW_MANY_HITS,
                hitsCount
            ),
            textAlign = TextAlign.Center
        )
    }
}