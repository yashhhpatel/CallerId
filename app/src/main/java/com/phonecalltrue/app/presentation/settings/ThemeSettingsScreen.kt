package com.phonecalltrue.app.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.data.model.ThemeMode
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.theme.AppDimens

@Composable
fun ThemeSettingsScreen(viewModel: AppViewModel, onBack: () -> Unit) {
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopBar(title = "App Theme", onBack = onBack)
        listOf(
            ThemeMode.SYSTEM to "System",
            ThemeMode.LIGHT to "Light Mode",
            ThemeMode.DARK to "Dark Mode"
        ).forEach { (mode, label) ->
            val selected = themeMode == mode
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(selected = selected, role = Role.RadioButton, onClick = { viewModel.setThemeMode(mode) })
                    .padding(horizontal = AppDimens.spaceM, vertical = AppDimens.spaceS),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = selected, onClick = null)
                Text(label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = AppDimens.spaceS))
            }
        }
    }
}
