package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.data.model.SpamCategory
import com.phonecalltrue.app.ui.theme.SafeGreen
import com.phonecalltrue.app.ui.theme.SpamRed
import com.phonecalltrue.app.ui.theme.TelemarketingOrange

fun SpamCategory.badgeColor(): Color = when (this) {
    SpamCategory.SAFE, SpamCategory.PERSONAL -> SafeGreen
    SpamCategory.SPAM, SpamCategory.FRAUD -> SpamRed
    SpamCategory.TELEMARKETING, SpamCategory.ROBOCALL -> TelemarketingOrange
    SpamCategory.BUSINESS -> Color(0xFF6366F1)
    SpamCategory.UNKNOWN -> Color(0xFF9CA3AF)
}

@Composable
fun SpamBadge(category: SpamCategory, modifier: Modifier = Modifier) {
    val color = category.badgeColor()
    Text(
        text = category.label.uppercase(),
        color = color,
        style = MaterialTheme.typography.labelSmall,
        modifier = modifier
            .background(color.copy(alpha = 0.12f), MaterialTheme.shapes.extraSmall)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}
