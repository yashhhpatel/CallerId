package com.phonecalltrue.app.presentation.onboarding

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
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Languages", style = MaterialTheme.typography.titleLarge) },
            actions = {
                IconButton(onClick = onConfirm) {
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
