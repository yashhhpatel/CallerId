package com.phonecalltrue.app.presentation.blocking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.data.model.BlockedNumber
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.components.EmptyState
import com.phonecalltrue.app.ui.components.InitialsAvatar
import com.phonecalltrue.app.ui.theme.AppDimens
import com.phonecalltrue.app.utils.PhoneNumberUtils

@Composable
fun CallBlockingScreen(viewModel: AppViewModel, onBack: () -> Unit) {
    val blocked by viewModel.blockedNumbers.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { DetailTopBar(title = "Call Blocking", onBack = onBack) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {
                Icon(Icons.Filled.Add, contentDescription = "Block a number")
            }
        }
    ) { padding ->
        if (blocked.isEmpty()) {
            EmptyState(
                icon = Icons.Filled.Block,
                title = "No Blocked Numbers",
                description = "Click on + icon to block contact",
                modifier = Modifier.fillMaxSize().padding(padding)
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
                items(blocked, key = { it.id }) { entry ->
                    BlockedRow(entry = entry, onUnblock = { viewModel.unblockNumber(entry.id) })
                }
            }
        }
    }

    if (showAddDialog) {
        AddBlockedNumberDialog(
            onConfirm = { number ->
                viewModel.blockNumber(
                    BlockedNumber(
                        id = number.hashCode().toString(),
                        phoneNumber = PhoneNumberUtils.displayFormat(number),
                        name = null,
                        reason = "Manually blocked"
                    )
                )
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }
}

@Composable
private fun BlockedRow(entry: BlockedNumber, onUnblock: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = AppDimens.spaceM, vertical = AppDimens.spaceS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialsAvatar(name = entry.name, seed = entry.id.hashCode())
        androidx.compose.foundation.layout.Column(modifier = Modifier.weight(1f).padding(horizontal = AppDimens.spaceM)) {
            Text(entry.name ?: entry.phoneNumber, style = MaterialTheme.typography.titleSmall)
            Text(entry.reason, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        IconButton(onClick = onUnblock) {
            Icon(Icons.Filled.Close, contentDescription = "Unblock", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun AddBlockedNumberDialog(onConfirm: (String) -> Unit, onDismiss: () -> Unit) {
    var value by remember { mutableStateOf("") }
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Block a number") },
        text = {
            com.phonecalltrue.app.ui.components.PhoneSearchField(
                value = value,
                onValueChange = { value = it },
                placeholder = "Enter phone number"
            )
        },
        confirmButton = {
            androidx.compose.material3.TextButton(
                onClick = { if (value.isNotBlank()) onConfirm(value) },
                enabled = value.isNotBlank()
            ) { Text("Block") }
        },
        dismissButton = { androidx.compose.material3.TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
