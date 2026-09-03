package com.phonecalltrue.app.presentation.onboarding

import android.app.role.RoleManager
import android.content.Context
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.ui.components.PrimaryButton
import com.phonecalltrue.app.ui.components.TextActionButton
import com.phonecalltrue.app.ui.theme.AppDimens

private data class DialerOption(val id: String, val label: String, val subtitle: String? = null)

@Composable
fun DefaultDialerScreen(onFinished: () -> Unit) {
    val context = LocalContext.current
    val options = remember {
        listOf(
            DialerOption("none", "None"),
            DialerOption("phone_call_true", "Phone Call True"),
            DialerOption("phone", "Phone"),
            DialerOption("truecaller", "Truecaller", "Current default")
        )
    }
    var selected by remember { mutableStateOf("truecaller") }

    val roleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { onFinished() }

    Column(modifier = Modifier.fillMaxSize().padding(AppDimens.spaceL)) {
        Text(
            text = "Set #Phone Call True as your default caller ID & spam app?",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        Column(modifier = Modifier.padding(top = AppDimens.spaceL)) {
            options.forEach { option ->
                val isSelected = selected == option.id
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(selected = isSelected, role = Role.RadioButton, onClick = { selected = option.id })
                        .padding(vertical = AppDimens.spaceXS),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = isSelected, onClick = null)
                    Column(modifier = Modifier.padding(start = AppDimens.spaceXS)) {
                        Text(option.label, style = MaterialTheme.typography.bodyLarge)
                        if (option.subtitle != null) {
                            Text(option.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spaceXL)) {
            TextActionButton(text = "Cancel", onClick = onFinished, modifier = Modifier.weight(1f))
            PrimaryButton(
                text = "Set as default",
                onClick = {
                    if (selected == "phone_call_true") {
                        requestCallerIdRole(context, roleLauncher) ?: onFinished()
                    } else {
                        onFinished()
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/** Uses the real RoleManager API on API 29+ to request the call-screening role; returns null if launched. */
private fun requestCallerIdRole(
    context: Context,
    launcher: androidx.activity.result.ActivityResultLauncher<android.content.Intent>
): Unit? {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return Unit
    val roleManager = context.getSystemService(Context.ROLE_SERVICE) as? RoleManager ?: return Unit
    if (!roleManager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING)) return Unit
    if (roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) return Unit
    launcher.launch(roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING))
    return null
}
