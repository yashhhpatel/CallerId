package com.phonecalltrue.app.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.data.local.PrivacyToggle
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.components.ToggleSettingItem
import com.phonecalltrue.app.ui.theme.AppDimens

@Composable
fun PrivacySettingsScreen(viewModel: AppViewModel, onBack: () -> Unit) {
    val callerId by viewModel.repository.prefs.callerIdEnabled.collectAsStateWithLifecycle(initialValue = true)
    val personalizedAds by viewModel.repository.prefs.personalizedAds.collectAsStateWithLifecycle(initialValue = true)
    val analytics by viewModel.repository.prefs.analyticsEnabled.collectAsStateWithLifecycle(initialValue = true)
    val dataSharing by viewModel.repository.prefs.dataSharingEnabled.collectAsStateWithLifecycle(initialValue = false)
    val locationSharing by viewModel.repository.prefs.locationSharingEnabled.collectAsStateWithLifecycle(initialValue = true)

    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopBar(title = "Privacy Setting", onBack = onBack)
        Column(modifier = Modifier.fillMaxSize().padding(AppDimens.spaceM)) {
            ToggleSettingItem(
                title = "Caller ID",
                subtitle = "Show identified names for incoming calls",
                checked = callerId,
                onCheckedChange = { viewModel.setPrivacyToggle(PrivacyToggle.CALLER_ID, it) }
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(top = AppDimens.spaceXS))
            ToggleSettingItem(
                title = "Location Permission",
                subtitle = "Location data will be collected and shared with third parties for analysis and measurement purposes",
                checked = locationSharing,
                onCheckedChange = { viewModel.setPrivacyToggle(PrivacyToggle.LOCATION_SHARING, it) }
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(top = AppDimens.spaceXS))
            ToggleSettingItem(
                title = "Personalized Recommendations",
                subtitle = "Use your activity to tailor ads and results",
                checked = personalizedAds,
                onCheckedChange = { viewModel.setPrivacyToggle(PrivacyToggle.PERSONALIZED_ADS, it) }
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(top = AppDimens.spaceXS))
            ToggleSettingItem(
                title = "Analytics",
                subtitle = "Help us improve by sharing crash and usage data",
                checked = analytics,
                onCheckedChange = { viewModel.setPrivacyToggle(PrivacyToggle.ANALYTICS, it) }
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(top = AppDimens.spaceXS))
            ToggleSettingItem(
                title = "Data Sharing",
                subtitle = "Share aggregated data with trusted partners",
                checked = dataSharing,
                onCheckedChange = { viewModel.setPrivacyToggle(PrivacyToggle.DATA_SHARING, it) }
            )
        }
    }
}
