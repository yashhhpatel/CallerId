package com.phonecalltrue.app.presentation.onboarding

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.phonecalltrue.app.ui.components.PrimaryButton
import com.phonecalltrue.app.ui.components.SecondaryButton
import com.phonecalltrue.app.ui.theme.AppDimens

@Composable
fun PrivacyOnboardingScreen(onAccept: () -> Unit, onDecline: () -> Unit) {
    val context = LocalContext.current
    val requested = remember { mutableStateOf(false) }

    val locationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { onAccept() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(AppDimens.spaceL)
    ) {
        Text(
            text = "Your comfort comes first!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Ensuring your privacy",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = AppDimens.spaceXS)
        )
        Text(
            text = "Improve your app experience by adding and viewing location details. This helps provide personalized and relevant results just for you. Location data is shared with third party partners.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AppDimens.spaceM)
        )
        Text(
            text = "We enhance your app experience by collecting:",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = AppDimens.spaceL)
        )
        listOf(
            "Precise location data and Device Identifiers",
            "Show location details for relevant results",
            "Deliver tailored ads and improve performance",
            "Perform Advertising and Location-based analytics",
            "Improve connectivity and app features on wireless networks"
        ).forEach { bullet ->
            Row(modifier = Modifier.padding(top = AppDimens.spaceXS)) {
                Text("•  ", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(bullet, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Text(
            text = "By tapping \"Accept\", you agree to this data collection and sharing with trusted partners. You can withdraw consent anytime in app settings.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AppDimens.spaceL)
        )

        Spacer(modifier = Modifier.padding(top = AppDimens.spaceXL))

        PrimaryButton(
            text = "ACCEPT",
            onClick = {
                requested.value = true
                val alreadyGranted = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                if (alreadyGranted) {
                    onAccept()
                } else {
                    locationLauncher.launch(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spaceM)
        )
        SecondaryButton(
            text = "DECLINE",
            onClick = onDecline,
            modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spaceS, bottom = AppDimens.spaceL)
        )
    }
}
