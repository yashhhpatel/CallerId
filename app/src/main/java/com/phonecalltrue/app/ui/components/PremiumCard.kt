package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.ui.theme.AppDimens

@Composable
fun PlanCard(
    title: String,
    price: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    highlight: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )
            .clickableRow(onClick)
            .padding(AppDimens.spaceM),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                if (highlight) {
                    Text(
                        "  BEST VALUE",
                        style = MaterialTheme.typography.labelSmall,
                        color = com.phonecalltrue.app.ui.theme.PremiumGold
                    )
                }
            }
            Text(price, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        RadioButton(selected = selected, onClick = onClick)
    }
}
