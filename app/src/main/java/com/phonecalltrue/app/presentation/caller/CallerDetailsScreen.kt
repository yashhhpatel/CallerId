package com.phonecalltrue.app.presentation.caller

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.data.model.BlockedNumber
import com.phonecalltrue.app.data.model.SpamCategory
import com.phonecalltrue.app.ui.components.ConfirmationDialog
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.components.InitialsAvatar
import com.phonecalltrue.app.ui.components.SpamBadge
import com.phonecalltrue.app.ui.theme.AppDimens

@Composable
fun CallerDetailsScreen(
    viewModel: AppViewModel,
    phoneNumber: String,
    onBack: () -> Unit
) {
    val result = remember(phoneNumber) { viewModel.lookupNumber(phoneNumber) }
    var showBlockConfirm by remember { mutableStateOf(false) }
    var showCallConfirm by remember { mutableStateOf(false) }
    var blocked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        DetailTopBar(title = "Caller Details", onBack = onBack)

        Column(
            modifier = Modifier.fillMaxWidth().padding(AppDimens.spaceL),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InitialsAvatar(name = result?.name, seed = phoneNumber.hashCode(), size = AppDimens.avatarSizeL)
            Text(
                text = result?.name ?: phoneNumber,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = AppDimens.spaceM)
            )
            if (result?.name != null) {
                Text(phoneNumber, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row(modifier = Modifier.padding(top = AppDimens.spaceS)) {
                SpamBadge(category = result?.category ?: SpamCategory.UNKNOWN)
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spaceXL),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionIcon(icon = Icons.Filled.Call, label = "Call") { showCallConfirm = true }
                ActionIcon(icon = Icons.Filled.Message, label = "Message") {}
                ActionIcon(icon = Icons.Filled.Block, label = if (blocked) "Unblock" else "Block") {
                    if (blocked) {
                        viewModel.unblockNumber(phoneNumber.hashCode().toString())
                        blocked = false
                    } else {
                        showBlockConfirm = true
                    }
                }
                ActionIcon(icon = Icons.Filled.Share, label = "Share") {}
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = AppDimens.spaceL)) {
            InfoRow(label = "Region", value = result?.region ?: "Unknown")
            InfoRow(label = "Reports", value = (result?.reportCount ?: 0).toString())
            InfoRow(label = "Category", value = (result?.category ?: SpamCategory.UNKNOWN).label)
        }
    }

    if (showCallConfirm) {
        ConfirmationDialog(
            title = "Call $phoneNumber?",
            message = "This is a demo build — no real call will be placed.",
            confirmLabel = "Call",
            onConfirm = { showCallConfirm = false },
            onDismiss = { showCallConfirm = false }
        )
    }
    if (showBlockConfirm) {
        ConfirmationDialog(
            title = "Block this caller?",
            message = "You won't receive calls or messages from $phoneNumber.",
            confirmLabel = "Block",
            isDestructive = true,
            onConfirm = {
                viewModel.blockNumber(
                    BlockedNumber(
                        id = phoneNumber.hashCode().toString(),
                        phoneNumber = phoneNumber,
                        name = result?.name,
                        reason = "Manually blocked"
                    )
                )
                blocked = true
                showBlockConfirm = false
            },
            onDismiss = { showBlockConfirm = false }
        )
    }
}

@Composable
private fun ActionIcon(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick) {
            Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.primary)
        }
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = AppDimens.spaceS),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}
