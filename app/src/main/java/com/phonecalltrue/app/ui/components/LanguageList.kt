package com.phonecalltrue.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.phonecalltrue.app.data.model.AVAILABLE_LANGUAGES
import com.phonecalltrue.app.ui.theme.AppDimens

@Composable
fun LanguageList(
    selectedCode: String,
    onLanguageSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(AVAILABLE_LANGUAGES, key = { it.code }) { language ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableRow { onLanguageSelected(language.code) }
                    .padding(horizontal = AppDimens.spaceM, vertical = AppDimens.spaceM),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(language.flagEmoji, style = MaterialTheme.typography.titleLarge)
                Text(
                    language.displayName,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f).padding(start = AppDimens.spaceM)
                )
                RadioButton(selected = language.code == selectedCode, onClick = { onLanguageSelected(language.code) })
            }
        }
    }
}
