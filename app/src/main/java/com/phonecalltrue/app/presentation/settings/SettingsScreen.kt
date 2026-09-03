package com.phonecalltrue.app.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Shield
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.data.model.AVAILABLE_LANGUAGES
import com.phonecalltrue.app.data.model.ThemeMode
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.components.SectionHeader
import com.phonecalltrue.app.ui.components.SettingsItem
import com.phonecalltrue.app.ui.theme.AppDimens
import androidx.compose.material.icons.filled.AccountCircle

@Composable
fun SettingsScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onLanguage: () -> Unit,
    onTheme: () -> Unit,
    onAccount: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onPrivacySettings: () -> Unit,
    onFeedback: () -> Unit,
    onAbout: () -> Unit
) {
    val languageCode by viewModel.languageCode.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val languageName = AVAILABLE_LANGUAGES.firstOrNull { it.code == languageCode }?.displayName ?: "English"
    val themeName = when (themeMode) {
        ThemeMode.SYSTEM -> "System"
        ThemeMode.LIGHT -> "Light Mode"
        ThemeMode.DARK -> "Dark Mode"
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopBar(title = "Settings", onBack = onBack)
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = AppDimens.spaceM)) {
            SectionHeader(title = "General")
            SettingsItem(icon = Icons.Filled.Language, title = "Language", subtitle = languageName, onClick = onLanguage)
            Spacer(modifier = Modifier.padding(top = AppDimens.spaceXS))
            SettingsItem(icon = Icons.Filled.Palette, title = "App Theme", subtitle = themeName, onClick = onTheme)
            Spacer(modifier = Modifier.padding(top = AppDimens.spaceXS))
            SettingsItem(icon = Icons.Filled.AccountCircle, title = "Account", onClick = onAccount)

            SectionHeader(title = "Privacy", modifier = Modifier.padding(top = AppDimens.spaceM))
            SettingsItem(icon = Icons.Filled.PrivacyTip, title = "Privacy Policy", onClick = onPrivacyPolicy)
            Spacer(modifier = Modifier.padding(top = AppDimens.spaceXS))
            SettingsItem(icon = Icons.Filled.Shield, title = "Privacy Setting", onClick = onPrivacySettings)

            SectionHeader(title = "Others", modifier = Modifier.padding(top = AppDimens.spaceM))
            SettingsItem(icon = Icons.Filled.Feedback, title = "Feedback", onClick = onFeedback)
            Spacer(modifier = Modifier.padding(top = AppDimens.spaceXS))
            SettingsItem(icon = Icons.Filled.Info, title = "About", onClick = onAbout)

            Spacer(modifier = Modifier.padding(top = AppDimens.spaceXL))
        }
    }
}
