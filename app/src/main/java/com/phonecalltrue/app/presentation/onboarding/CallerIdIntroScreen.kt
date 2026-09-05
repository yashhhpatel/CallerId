package com.phonecalltrue.app.presentation.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.ui.components.ConfirmationDialog
import com.phonecalltrue.app.ui.components.PrimaryButton
import com.phonecalltrue.app.ui.components.TextActionButton
import com.phonecalltrue.app.ui.theme.AppDimens
import com.phonecalltrue.app.ui.theme.SpamRed

@Composable
fun CallerIdIntroScreen(onAccept: () -> Unit, onDecline: () -> Unit, onOpenPrivacyPolicy: () -> Unit = {}) {
    var showDeclineConfirm by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(AppDimens.spaceL)) {
        Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            MockIncomingCallCard()
        }

        Text(
            text = "Enhanced Caller ID",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Identify unknown callers with caller name and avoid unwanted spam calls.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spaceS)
        )
        val disclaimer = buildAnnotatedString {
            append("By enabling this feature, you agree that your contacts will be used to improve the accuracy of the service, and you accept the ")
            pushStringAnnotation(tag = "link", annotation = "policy")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
                append("Terms of use")
            }
            pop()
            append(" and ")
            pushStringAnnotation(tag = "link", annotation = "policy")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
                append("Privacy Policy")
            }
            pop()
            append(".")
        }
        ClickableText(
            text = disclaimer,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spaceM, bottom = AppDimens.spaceL),
            onClick = { offset ->
                disclaimer.getStringAnnotations("link", offset, offset).firstOrNull()?.let { onOpenPrivacyPolicy() }
            }
        )

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TextActionButton(text = "DECLINE", onClick = { showDeclineConfirm = true })
            Spacer(modifier = Modifier.padding(horizontal = AppDimens.spaceXS))
            PrimaryButton(text = "ACCEPT", onClick = onAccept, modifier = Modifier.weight(1f))
        }
    }

    if (showDeclineConfirm) {
        ConfirmationDialog(
            title = "Continue without Enhanced Caller ID?",
            message = "Caller ID results might be limited or unavailable.",
            confirmLabel = "Skip",
            dismissLabel = "Cancel",
            onConfirm = { showDeclineConfirm = false; onDecline() },
            onDismiss = { showDeclineConfirm = false }
        )
    }
}

@Composable
private fun MockIncomingCallCard() {
    Box(
        modifier = Modifier
            .size(240.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Phone silhouette with the incoming-call text on its "screen"
        Box(
            modifier = Modifier
                .size(width = 132.dp, height = 220.dp)
                .border(BorderStroke(10.dp, Color(0xFF1B1D22)), RoundedCornerShape(28.dp))
                .background(Color(0xFF6B7280), RoundedCornerShape(18.dp))
                .padding(top = AppDimens.spaceL),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "(889) 953-7072",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text("Incoming call", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.85f))
            }
        }

        // Caller-info card overlapping the bottom of the phone
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-36).dp)
                .fillMaxWidth(0.88f)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(14.dp))
                .padding(AppDimens.spaceS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(36.dp).background(SpamRed.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Face, contentDescription = null, tint = SpamRed, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f).padding(start = AppDimens.spaceXS)) {
                Text("Telemarketing", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text("3281 Reports", style = MaterialTheme.typography.labelSmall, color = SpamRed)
            }
            Box(
                modifier = Modifier.size(28.dp).background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.VerifiedUser, contentDescription = "Verified by Phone Call True", tint = Color.White, modifier = Modifier.size(16.dp))
            }
        }
    }
}
