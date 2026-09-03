package com.phonecalltrue.app.presentation.region

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.components.RegionItem

@Composable
fun RegionDetailScreen(
    viewModel: AppViewModel,
    regionId: String,
    regionName: String,
    onBack: () -> Unit
) {
    val cities = remember(regionId) { viewModel.regionCities(regionId) }

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopBar(title = regionName, onBack = onBack)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cities, key = { it.id }) { city ->
                RegionItem(region = city)
            }
        }
    }
}
