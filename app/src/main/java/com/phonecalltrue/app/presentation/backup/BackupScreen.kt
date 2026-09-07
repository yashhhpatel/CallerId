package com.phonecalltrue.app.presentation.backup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.phonecalltrue.app.ui.components.AdBanner
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.components.PrimaryButton
import com.phonecalltrue.app.ui.theme.AppDimens
import kotlinx.coroutines.launch

@Composable
fun BackupScreen(onBack: () -> Unit) {
    var lastBackup by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    androidx.compose.material3.Scaffold(
        topBar = { DetailTopBar(title = "Backup & Restore", onBack = onBack) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(AppDimens.spaceL)) {
            BackupCard(
                icon = Icons.Filled.CloudUpload,
                title = "Backup",
                description = "Back up your contacts to Google Drive and restore them when you need.",
                footnote = "Last backup: ${lastBackup ?: "Never"}",
                actionLabel = "BACKUP",
                onAction = {
                    lastBackup = "Just now"
                    scope.launch { snackbarHostState.showSnackbar("Backup completed successfully") }
                }
            )
            BackupCard(
                icon = Icons.Filled.CloudDone,
                title = "Restore",
                description = "You can restore your contact list now.",
                footnote = null,
                actionLabel = "RESTORE",
                onAction = {
                    scope.launch { snackbarHostState.showSnackbar("Contacts restored") }
                },
                modifier = Modifier.padding(top = AppDimens.spaceL)
            )

            AdBanner(modifier = Modifier.padding(top = AppDimens.spaceL))

            Text(
                text = "Google Drive Settings",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = AppDimens.spaceXL, bottom = AppDimens.spaceXS)
            )
            Text(
                text = "Here are some options for individuals on how you can back up your data to Google Drive.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            InfoRow(label = "Google Drive account", value = "Choose Account")
            InfoRow(label = "Backup frequency", value = "Never")
            InfoRow(label = "Back up over", value = "Wi-Fi")
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = AppDimens.spaceS),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun BackupCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    footnote: String?,
    actionLabel: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
            .padding(AppDimens.spaceL)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = AppDimens.spaceS))
        }
        Text(description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = AppDimens.spaceXS))
        if (footnote != null) {
            Text(footnote, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = AppDimens.spaceXS))
        }
        PrimaryButton(text = actionLabel, onClick = onAction, modifier = Modifier.padding(top = AppDimens.spaceM))
    }
}
