package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.phonecalltrue.app.ui.theme.AppDimens
import com.phonecalltrue.app.ui.theme.AvatarPalette

@Composable
fun InitialsAvatar(
    name: String?,
    seed: Int,
    modifier: Modifier = Modifier,
    size: Dp = AppDimens.avatarSizeM
) {
    val color = AvatarPalette[Math.floorMod(seed, AvatarPalette.size)]
    val initial = name?.trim()?.firstOrNull()?.uppercaseChar()?.toString() ?: "#"
    Box(
        modifier = modifier
            .size(size)
            .background(color.copy(alpha = if (name != null) 1f else 0.5f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
