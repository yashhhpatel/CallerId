package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/** Fast-scroll A-Z index rail used on the Contacts screen, matching the reference video. */
@Composable
fun AlphabetIndexRail(
    letters: List<Char>,
    onLetterSelected: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(20.dp)
            .fillMaxHeight()
            .padding(vertical = 4.dp)
            .pointerInput(letters) {
                detectDragGestures { change, _ ->
                    val index = (change.position.y / (size.height.toFloat() / letters.size))
                        .toInt()
                        .coerceIn(0, letters.size - 1)
                    onLetterSelected(letters[index])
                }
            },
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
    ) {
        letters.forEach { letter ->
            Text(
                text = letter.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(20.dp)
                    .clickableRow { onLetterSelected(letter) }
            )
        }
    }
}
