package com.phonecalltrue.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FloatingDialPadButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        shape = androidx.compose.foundation.shape.CircleShape,
        modifier = modifier
    ) {
        Icon(Icons.Filled.Dialpad, contentDescription = "Open dial pad")
    }
}
