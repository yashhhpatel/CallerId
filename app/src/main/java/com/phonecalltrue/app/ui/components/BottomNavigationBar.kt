package com.phonecalltrue.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.phonecalltrue.app.navigation.Routes

enum class BottomTab(val route: String, val label: String) {
    RECENTS(Routes.RECENTS, "Recents"),
    CONTACTS(Routes.CONTACTS, "Contacts"),
    NO_ADS(Routes.NO_ADS, "No Ad's"),
    REGION(Routes.REGION, "Region")
}

@Composable
fun BottomNavigationBar(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        BottomTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = when (tab) {
                            BottomTab.RECENTS -> Icons.Filled.History
                            BottomTab.CONTACTS -> Icons.Filled.Person
                            BottomTab.NO_ADS -> Icons.Filled.WorkspacePremium
                            BottomTab.REGION -> Icons.Filled.Public
                        },
                        contentDescription = tab.label
                    )
                },
                label = { Text(tab.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}
