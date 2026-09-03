package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

/** Standard ripple-backed clickable row used across list items and drawer entries. */
fun Modifier.clickableRow(onClick: () -> Unit): Modifier = this.clickable(onClick = onClick)
