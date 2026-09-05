package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppFab(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        shape = CircleShape,
        modifier = modifier
    ) {
        Icon(icon, contentDescription = contentDescription)
    }
}

@Composable
fun FloatingDialPadButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppFab(icon = Icons.Filled.Dialpad, contentDescription = "Open dial pad", onClick = onClick, modifier = modifier)
}

@Composable
fun AddContactFab(onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppFab(icon = Icons.Filled.PersonAdd, contentDescription = "Add contact", onClick = onClick, modifier = modifier)
}
