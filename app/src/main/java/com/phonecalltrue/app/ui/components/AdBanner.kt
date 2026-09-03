package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Phone
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
import com.phonecalltrue.app.ui.theme.AppDimens

/**
 * Replaceable placeholder ad unit. Swap the body for a real AdMob/mediation
 * banner later — callers only depend on this composable's slot in the layout.
 */
@Composable
fun AdBanner(modifier: Modifier = Modifier, dismissible: Boolean = true) {
    var dismissed by remember { mutableStateOf(false) }
    if (dismissed) return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(0.dp))
            .padding(horizontal = AppDimens.spaceM, vertical = AppDimens.spaceS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Phone, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(AppDimens.spaceS))
        Text(
            text = "Sponsored · Try Now",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "Advertisement",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (dismissible) {
            IconButton(onClick = { dismissed = true }, modifier = Modifier.size(28.dp)) {
                Icon(Icons.Filled.Close, contentDescription = "Dismiss ad", modifier = Modifier.size(16.dp))
            }
        }
    }
}
