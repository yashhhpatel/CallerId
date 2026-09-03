package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.phonecalltrue.app.ui.theme.PremiumGold

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    confirmLabel: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    dismissLabel: String = "Cancel",
    isDestructive: Boolean = false
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmLabel, color = if (isDestructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(dismissLabel) }
        }
    )
}

@Composable
fun PermissionRationaleDialog(
    title: String,
    message: String,
    onAllow: () -> Unit,
    onDeny: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDeny,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = { TextButton(onClick = onAllow) { Text("Allow") } },
        dismissButton = { TextButton(onClick = onDeny) { Text("Don't allow") } }
    )
}

@Composable
fun RateUsDialog(
    onRateNow: (stars: Int) -> Unit,
    onMaybeLater: () -> Unit
) {
    var stars by remember { mutableIntStateOf(5) }
    AlertDialog(
        onDismissRequest = onMaybeLater,
        title = { Text("Enjoying Phone Call True?") },
        text = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center) {
                (1..5).forEach { i ->
                    IconButton(onClick = { stars = i }) {
                        Icon(
                            imageVector = if (i <= stars) Icons.Filled.Star else Icons.Filled.StarBorder,
                            contentDescription = "$i star",
                            tint = PremiumGold
                        )
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = { onRateNow(stars) }) { Text("Rate now") } },
        dismissButton = { TextButton(onClick = onMaybeLater) { Text("Maybe later") } }
    )
}
