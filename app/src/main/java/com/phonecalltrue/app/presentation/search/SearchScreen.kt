package com.phonecalltrue.app.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.data.model.SearchResult
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.components.EmptyState
import com.phonecalltrue.app.ui.components.InitialsAvatar
import com.phonecalltrue.app.ui.components.PhoneSearchField
import com.phonecalltrue.app.ui.components.SpamBadge
import com.phonecalltrue.app.ui.theme.AppDimens
import com.phonecalltrue.app.utils.PhoneNumberUtils

@Composable
fun SearchScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onResultClick: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val history by viewModel.searchHistory.collectAsStateWithLifecycle()

    val result: SearchResult? = remember(query) {
        if (PhoneNumberUtils.normalize(query).length >= 5) viewModel.lookupNumber(query) else null
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopBar(title = "Search Phone Number", onBack = onBack)
        PhoneSearchField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth().padding(AppDimens.spaceM)
        )

        when {
            query.isBlank() -> {
                if (history.isEmpty()) {
                    EmptyState(
                        icon = Icons.Filled.Search,
                        title = "No recent searches",
                        description = "Search for a number to find who's calling.",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = AppDimens.spaceM),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Recent Searches", style = MaterialTheme.typography.titleSmall)
                        TextButton(onClick = { viewModel.clearSearchHistory() }) { Text("Clear") }
                    }
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(history, key = { it }) { number ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = AppDimens.spaceM, vertical = AppDimens.spaceS),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Filled.History, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(number, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = AppDimens.spaceM))
                            }
                        }
                    }
                }
            }
            result == null -> {
                EmptyState(
                    icon = Icons.Filled.Search,
                    title = "No result",
                    description = "We couldn't find any information for \"$query\". Try a full 10-digit number.",
                    modifier = Modifier.fillMaxSize()
                )
            }
            else -> {
                Column(modifier = Modifier.fillMaxWidth().padding(AppDimens.spaceM)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        InitialsAvatar(name = result.name, seed = result.phoneNumber.hashCode())
                        Column(modifier = Modifier.padding(start = AppDimens.spaceM)) {
                            Text(result.name ?: result.phoneNumber, style = MaterialTheme.typography.titleMedium)
                            if (result.name != null) {
                                Text(result.phoneNumber, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                    Row(modifier = Modifier.padding(top = AppDimens.spaceS)) {
                        SpamBadge(category = result.category)
                    }
                    Text(
                        text = "Region: ${result.region}" + if (result.reportCount > 0) " · ${result.reportCount} reports" else "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = AppDimens.spaceS)
                    )
                    com.phonecalltrue.app.ui.components.PrimaryButton(
                        text = "View Details",
                        onClick = {
                            viewModel.addSearchHistoryEntry(result.phoneNumber)
                            onResultClick(result.phoneNumber)
                        },
                        modifier = Modifier.padding(top = AppDimens.spaceL)
                    )
                }
            }
        }
    }
}
