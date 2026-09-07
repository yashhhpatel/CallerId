package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallMissed
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.data.model.CallRecord
import com.phonecalltrue.app.data.model.CallType
import com.phonecalltrue.app.data.model.Contact
import com.phonecalltrue.app.data.model.RegionStat
import com.phonecalltrue.app.ui.theme.AppDimens
import com.phonecalltrue.app.ui.theme.SafeGreen
import com.phonecalltrue.app.ui.theme.SpamRed

private fun CallType.icon(): ImageVector = when (this) {
    CallType.INCOMING -> Icons.AutoMirrored.Filled.CallReceived
    CallType.OUTGOING -> Icons.AutoMirrored.Filled.CallMade
    CallType.MISSED -> Icons.AutoMirrored.Filled.CallMissed
    CallType.BLOCKED -> Icons.Filled.Block
}

private fun CallType.tint() = when (this) {
    CallType.INCOMING -> SafeGreen
    CallType.OUTGOING -> SafeGreen
    CallType.MISSED -> SpamRed
    CallType.BLOCKED -> SpamRed
}

@Composable
fun CallHistoryItem(
    record: CallRecord,
    onClick: () -> Unit,
    onCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickableRow(onClick)
            .padding(horizontal = AppDimens.spaceM, vertical = AppDimens.spaceS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialsAvatar(name = record.name, seed = record.avatarColorSeed)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = AppDimens.spaceM)
        ) {
            Text(
                text = record.name ?: record.phoneNumber,
                style = MaterialTheme.typography.titleSmall,
                color = if (record.callType == CallType.MISSED) SpamRed else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.padding(top = 2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = record.callType.icon(),
                    contentDescription = record.callType.name,
                    tint = record.callType.tint(),
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "  ${record.timeLabel}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        IconButton(onClick = onCallClick) {
            Icon(Icons.Filled.Call, contentDescription = "Call ${record.name ?: record.phoneNumber}", tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    onClick: () -> Unit,
    onCallClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickableRow(onClick)
            .padding(horizontal = AppDimens.spaceM, vertical = AppDimens.spaceS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialsAvatar(name = contact.name, seed = contact.avatarColorSeed)
        Column(modifier = Modifier.weight(1f).padding(horizontal = AppDimens.spaceM)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(contact.name, style = MaterialTheme.typography.titleSmall)
                if (contact.isFavorite) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Favorite",
                        tint = com.phonecalltrue.app.ui.theme.PremiumGold,
                        modifier = Modifier.padding(start = 4.dp).size(14.dp)
                    )
                }
            }
            Text(contact.phoneNumber, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        IconButton(onClick = onCallClick) {
            Icon(Icons.Filled.Call, contentDescription = "Call ${contact.name}", tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun RegionItem(
    region: RegionStat,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickableRow(onClick) else Modifier)
            .padding(horizontal = AppDimens.spaceM, vertical = AppDimens.spaceS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LetterAvatar(name = region.name, seed = region.id.hashCode(), size = AppDimens.avatarSizeS)
        Text(
            text = region.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f).padding(horizontal = AppDimens.spaceM)
        )
        Text(
            text = region.count.toString(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (onClick != null) {
            Icon(
                Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    destructive: Boolean = false
) {
    val contentColor = if (destructive) SpamRed else MaterialTheme.colorScheme.onSurface
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .clickableRow(onClick)
            .padding(AppDimens.spaceM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = if (destructive) SpamRed else MaterialTheme.colorScheme.primary)
        Column(modifier = Modifier.weight(1f).padding(horizontal = AppDimens.spaceM)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, color = contentColor)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun ToggleSettingItem(
    title: String,
    subtitle: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .padding(AppDimens.spaceM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Spacer(modifier = Modifier.width(AppDimens.spaceS))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedTrackColor = MaterialTheme.colorScheme.primary)
        )
    }
}
