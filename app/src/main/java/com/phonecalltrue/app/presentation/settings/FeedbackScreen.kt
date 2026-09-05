package com.phonecalltrue.app.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.ui.components.PrimaryButton
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.theme.AppDimens

private val feedbackTypes = listOf("Bug report", "Feature request", "Spam report issue", "Other")

@Composable
fun FeedbackScreen(userEmail: String, onBack: () -> Unit) {
    var selectedType by remember { mutableStateOf(feedbackTypes.first()) }
    var message by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopBar(title = "Feedback", onBack = onBack)
        Column(modifier = Modifier.fillMaxSize().padding(AppDimens.spaceL)) {
            if (submitted) {
                Text("Thanks for your feedback!", style = MaterialTheme.typography.titleMedium)
                Text(
                    "We read every message and use it to improve Phone Call True.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AppDimens.spaceXS)
                )
                return@Column
            }

            Text("Feedback type", style = MaterialTheme.typography.titleSmall)
            Row(
                modifier = Modifier
                    .padding(top = AppDimens.spaceS)
                    .horizontalScroll(rememberScrollState())
            ) {
                feedbackTypes.forEach { type ->
                    val selected = type == selectedType
                    Text(
                        text = type,
                        style = MaterialTheme.typography.labelMedium,
                        softWrap = false,
                        color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(end = AppDimens.spaceXS)
                            .background(
                                if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                MaterialTheme.shapes.small
                            )
                            .clickable { selectedType = type }
                            .padding(horizontal = AppDimens.spaceS, vertical = AppDimens.spaceXS)
                    )
                }
            }

            Text("Message", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(top = AppDimens.spaceL))
            BasicTextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(top = AppDimens.spaceS)
                    .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
                    .padding(AppDimens.spaceM),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
            )
            Text(
                text = "${message.length}/500",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
            )

            Text("Your email (optional)", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(top = AppDimens.spaceM))
            Text(userEmail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))

            PrimaryButton(
                text = "Submit",
                enabled = message.trim().length >= 10,
                onClick = { submitted = true },
                modifier = Modifier.padding(top = AppDimens.spaceXL)
            )
        }
    }
}
