package com.phonecalltrue.app.presentation.onboarding

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.phonecalltrue.app.ui.components.LanguageList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageOnboardingScreen(
    selectedCode: String,
    onLanguageSelected: (String) -> Unit,
    onConfirm: () -> Unit
) {
    // Real Android runtime permission requests — shown as native system dialogs,
    // matching the reference video's call-log / contacts / notifications prompts.
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { onConfirm() }

    val requestedPermissions = buildList {
        add(Manifest.permission.READ_CALL_LOG)
        add(Manifest.permission.READ_CONTACTS)
        add(Manifest.permission.CALL_PHONE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }.toTypedArray()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Languages", style = MaterialTheme.typography.titleLarge) },
            actions = {
                IconButton(onClick = { permissionLauncher.launch(requestedPermissions) }) {
                    Icon(Icons.Filled.Check, contentDescription = "Confirm language")
                }
            }
        )
        LanguageList(
            selectedCode = selectedCode,
            onLanguageSelected = onLanguageSelected,
            modifier = Modifier.weight(1f)
        )
    }
}
