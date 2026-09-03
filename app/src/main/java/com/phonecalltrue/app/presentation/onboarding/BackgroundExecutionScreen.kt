package com.phonecalltrue.app.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.phonecalltrue.app.ui.components.PrimaryButton
import com.phonecalltrue.app.ui.components.SecondaryButton
import com.phonecalltrue.app.ui.theme.AppDimens

@Composable
fun BackgroundExecutionScreen(onAllow: () -> Unit, onDecline: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimens.spaceXL)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.large)
                .padding(AppDimens.spaceL)
        ) {
            Text(
                text = "Let app always run in background?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Allowing Phone Call True to always run in the background may reduce battery life. You can change this later from Settings > Apps.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = AppDimens.spaceM)
            )
            PrimaryButton(text = "Allow", onClick = onAllow, modifier = Modifier.padding(top = AppDimens.spaceL))
            SecondaryButton(text = "Decline", onClick = onDecline, modifier = Modifier.padding(top = AppDimens.spaceS))
        }
    }
}
