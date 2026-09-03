package com.phonecalltrue.app.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.ui.components.ConfirmationDialog
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.components.SecondaryButton
import com.phonecalltrue.app.ui.theme.AppDimens
import com.phonecalltrue.app.ui.theme.SpamRed

@Composable
fun AccountScreen(userEmail: String, onBack: () -> Unit) {
    var signedIn by remember { mutableStateOf(true) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopBar(title = "Account", onBack = onBack)
        Column(modifier = Modifier.fillMaxWidth().padding(AppDimens.spaceL)) {
            if (signedIn) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier.size(56.dp).background(MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White)
                    }
                    Column(modifier = Modifier.padding(start = AppDimens.spaceM)) {
                        Text("Chintan Patel", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Text(userEmail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

                Text(
                    text = "Linked Account",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = AppDimens.spaceXL)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spaceS),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    Text("Google", style = MaterialTheme.typography.bodyLarge)
                    Text("Connected", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                }

                SecondaryButton(text = "Log out", onClick = { signedIn = false }, modifier = Modifier.padding(top = AppDimens.spaceXL))
                androidx.compose.material3.Button(
                    onClick = { showDeleteConfirm = true },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = SpamRed),
                    modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spaceS)
                ) {
                    Text("Delete Account")
                }
            } else {
                Text("You're signed out.", style = MaterialTheme.typography.titleMedium)
                com.phonecalltrue.app.ui.components.PrimaryButton(
                    text = "Sign in with Google",
                    onClick = { signedIn = true },
                    modifier = Modifier.padding(top = AppDimens.spaceM)
                )
            }
        }
    }

    if (showDeleteConfirm) {
        ConfirmationDialog(
            title = "Delete Account",
            message = "All data related to your account will be deleted. This cannot be undone.",
            confirmLabel = "Delete Account",
            isDestructive = true,
            onConfirm = { signedIn = false; showDeleteConfirm = false },
            onDismiss = { showDeleteConfirm = false }
        )
    }
}
