package com.phonecalltrue.app.presentation.dialpad

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.ui.components.ConfirmationDialog
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.utils.PhoneNumberUtils

private val keys = listOf(
    listOf("1", "2", "3"),
    listOf("4", "5", "6"),
    listOf("7", "8", "9"),
    listOf("*", "0", "#")
)

@Composable
fun DialPadScreen(onBack: () -> Unit) {
    var number by remember { mutableStateOf("") }
    var showCallConfirm by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopBar(title = "Dial Pad", onBack = onBack)

        Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = if (number.isEmpty()) "Enter a number" else PhoneNumberUtils.displayFormat(number),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = if (number.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
        }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
            keys.forEach { row ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    row.forEach { key ->
                        DialKey(key = key, onClick = { number += key })
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { if (number.isNotEmpty()) showCallConfirm = true }) {
                    Icon(Icons.Filled.Call, contentDescription = "Call", tint = Color.White)
                }
            }
            if (number.isNotEmpty()) {
                IconButton(
                    onClick = { number = number.dropLast(1) },
                    modifier = Modifier.padding(start = 24.dp)
                ) {
                    Icon(Icons.Filled.Backspace, contentDescription = "Backspace")
                }
            }
        }
    }

    if (showCallConfirm) {
        ConfirmationDialog(
            title = "Call ${PhoneNumberUtils.displayFormat(number)}?",
            message = "This is a demo build — no real call will be placed.",
            confirmLabel = "Call",
            onConfirm = { showCallConfirm = false },
            onDismiss = { showCallConfirm = false }
        )
    }
}

@Composable
private fun DialKey(key: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(68.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(68.dp)) {
            Text(key, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

