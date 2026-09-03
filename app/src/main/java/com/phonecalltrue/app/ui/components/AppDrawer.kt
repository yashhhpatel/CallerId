package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.ui.theme.AppDimens

data class DrawerAction(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val onClick: () -> Unit)

@Composable
fun AppDrawerContent(
    onCallBlocking: () -> Unit,
    onBackup: () -> Unit,
    onShareCallerId: () -> Unit,
    onRateUs: () -> Unit,
    onSettings: () -> Unit
) {
    ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppDimens.spaceL),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Phone, contentDescription = null, tint = Color.White)
                }
                Text(
                    text = "Phone Call True",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = AppDimens.spaceM)
                )
            }

            val actions = listOf(
                DrawerAction("Call Blocking", Icons.Filled.Block, onCallBlocking),
                DrawerAction("Backup", Icons.Filled.Backup, onBackup),
                DrawerAction("Share Caller ID", Icons.Filled.Share, onShareCallerId),
                DrawerAction("Rate Us", Icons.Filled.Star, onRateUs)
            )
            actions.forEach { action ->
                DrawerRow(action.label, action.icon, action.onClick)
            }

            Box(modifier = Modifier.height(1.dp).fillMaxWidth().padding(vertical = AppDimens.spaceS).background(MaterialTheme.colorScheme.outline))

            DrawerRow("Settings", Icons.Filled.Settings, onSettings)

            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Version 9.10.28",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(AppDimens.spaceL)
                )
            }
        }
    }
}

@Composable
private fun DrawerRow(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickableRow(onClick)
            .padding(horizontal = AppDimens.spaceL, vertical = AppDimens.spaceM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = AppDimens.spaceM))
    }
}
