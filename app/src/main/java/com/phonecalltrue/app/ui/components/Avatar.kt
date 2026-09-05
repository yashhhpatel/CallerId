package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.phonecalltrue.app.ui.theme.AppDimens

/**
 * Plain gray silhouette avatar, matching the reference app's neutral contact/call
 * icons (spam/missed status is conveyed by the row's text color, not the avatar).
 */
@Composable
fun InitialsAvatar(
    name: String?,
    seed: Int,
    modifier: Modifier = Modifier,
    size: Dp = AppDimens.avatarSizeM
) {
    Box(
        modifier = modifier
            .size(size)
            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(size * 0.6f)
        )
    }
}
