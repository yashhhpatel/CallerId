package com.phonecalltrue.app.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.components.LanguageList

@Composable
fun LanguageSettingsScreen(viewModel: AppViewModel, onBack: () -> Unit) {
    val languageCode by viewModel.languageCode.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopBar(title = "Languages", onBack = onBack)
        LanguageList(
            selectedCode = languageCode,
            onLanguageSelected = { viewModel.setLanguageCode(it) },
            modifier = Modifier.weight(1f)
        )
    }
}
