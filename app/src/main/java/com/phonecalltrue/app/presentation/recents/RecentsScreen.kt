package com.phonecalltrue.app.presentation.recents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.ui.components.AdBanner
import com.phonecalltrue.app.ui.components.CallHistoryItem
import com.phonecalltrue.app.ui.components.EmptyState
import com.phonecalltrue.app.ui.components.SectionHeader

@Composable
fun RecentsScreen(
    viewModel: AppViewModel,
    showAds: Boolean,
    onCallClick: (String) -> Unit
) {
    val calls by viewModel.calls.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        if (calls.isEmpty()) {
            EmptyState(
                icon = Icons.Filled.History,
                title = "No Recent Calls",
                description = "Calls you make or receive will show up here.",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                val grouped = calls.groupBy { it.dateGroup }
                grouped.forEach { (group, records) ->
                    item(key = "header_$group") { SectionHeader(title = group) }
                    items(records, key = { it.id }) { record ->
                        CallHistoryItem(
                            record = record,
                            onClick = { onCallClick(record.phoneNumber) },
                            onCallClick = { onCallClick(record.phoneNumber) },
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
            if (showAds) AdBanner()
        }
    }
}
