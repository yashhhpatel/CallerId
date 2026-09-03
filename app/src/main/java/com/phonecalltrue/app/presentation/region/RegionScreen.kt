package com.phonecalltrue.app.presentation.region

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.ui.components.AdBanner
import com.phonecalltrue.app.ui.components.RegionItem
import com.phonecalltrue.app.ui.theme.AppDimens

@Composable
fun RegionScreen(
    viewModel: AppViewModel,
    showAds: Boolean,
    onRegionClick: (String, String) -> Unit
) {
    val regions by viewModel.regions.collectAsStateWithLifecycle()
    val total = regions.sumOf { it.count }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "India ($total)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(AppDimens.spaceM)
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(regions, key = { it.id }) { region ->
                RegionItem(region = region, onClick = { onRegionClick(region.id, region.name) })
            }
        }
        if (showAds) AdBanner()
    }
}
