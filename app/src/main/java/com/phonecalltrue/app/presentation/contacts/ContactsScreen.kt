package com.phonecalltrue.app.presentation.contacts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.ui.components.AdBanner
import com.phonecalltrue.app.ui.components.AlphabetIndexRail
import com.phonecalltrue.app.ui.components.ContactItem
import com.phonecalltrue.app.ui.components.EmptyState
import com.phonecalltrue.app.ui.components.PhoneSearchField
import com.phonecalltrue.app.ui.components.SectionHeader
import com.phonecalltrue.app.ui.theme.AppDimens
import kotlinx.coroutines.launch

@Composable
fun ContactsScreen(
    viewModel: AppViewModel,
    showAds: Boolean,
    onContactClick: (String) -> Unit
) {
    val contacts by viewModel.contacts.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }

    val filtered = remember(contacts, query) {
        if (query.isBlank()) contacts
        else contacts.filter { it.name.contains(query, ignoreCase = true) || it.phoneNumber.contains(query) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        PhoneSearchField(
            value = query,
            onValueChange = { query = it },
            placeholder = "Search contacts",
            modifier = Modifier.fillMaxWidth().padding(AppDimens.spaceM)
        )
        if (contacts.isEmpty()) {
            EmptyState(
                icon = Icons.Filled.Contacts,
                title = "No Contacts Yet",
                description = "Your saved contacts will appear here.",
                modifier = Modifier.fillMaxSize()
            )
        } else if (filtered.isEmpty()) {
            EmptyState(
                icon = Icons.Filled.Contacts,
                title = "No Matches",
                description = "No contacts matched \"$query\".",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            val listState = rememberLazyListState()
            val scope = rememberCoroutineScope()
            val favorites = filtered.filter { it.isFavorite }
            val showFavorites = favorites.isNotEmpty() && query.isBlank()
            val grouped = filtered.groupBy { it.name.first().uppercaseChar() }.toSortedMap()

            // Map each letter to its section-header item index within the LazyColumn.
            val letterIndex = remember(grouped, showFavorites) {
                val map = mutableMapOf<Char, Int>()
                var index = if (showFavorites) 1 + favorites.size else 0
                grouped.forEach { (letter, group) ->
                    map[letter] = index
                    index += 1 + group.size
                }
                map
            }

            Row(modifier = Modifier.weight(1f)) {
                LazyColumn(state = listState, modifier = Modifier.weight(1f)) {
                    if (showFavorites) {
                        item(key = "favorites_header") { SectionHeader(title = "Your Favorites") }
                        items(favorites, key = { "fav_${it.id}" }) { contact ->
                            ContactItem(contact = contact, onClick = { onContactClick(contact.phoneNumber) }, onCallClick = { onContactClick(contact.phoneNumber) })
                        }
                    }
                    grouped.forEach { (letter, group) ->
                        item(key = "letter_$letter") { SectionHeader(title = letter.toString()) }
                        items(group, key = { it.id }) { contact ->
                            ContactItem(contact = contact, onClick = { onContactClick(contact.phoneNumber) }, onCallClick = { onContactClick(contact.phoneNumber) })
                        }
                    }
                }
                if (query.isBlank() && grouped.size > 3) {
                    AlphabetIndexRail(
                        letters = grouped.keys.toList(),
                        onLetterSelected = { letter ->
                            letterIndex[letter]?.let { idx ->
                                scope.launch { listState.animateScrollToItem(idx) }
                            }
                        },
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
            if (showAds) AdBanner()
        }
    }
}
