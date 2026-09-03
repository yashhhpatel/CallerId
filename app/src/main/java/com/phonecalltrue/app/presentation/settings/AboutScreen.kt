package com.phonecalltrue.app.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.theme.AppDimens

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopBar(title = "About", onBack = onBack)
        Column(
            modifier = Modifier.fillMaxSize().padding(AppDimens.spaceXL),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(64.dp).background(MaterialTheme.colorScheme.primary, RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Phone, contentDescription = null, tint = Color.White)
            }
            Text("Phone Call True", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = AppDimens.spaceM))
            Text("Version 9.10.28", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                text = "Phone Call True app has arrived to replace your old phone dialer & bring your calling experience to the next level! Caller ID is highly customizable but easy to use.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = AppDimens.spaceL)
            )
        }
    }
}
