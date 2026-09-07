package com.phonecalltrue.app.presentation.noads

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.PhoneDisabled
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.ui.components.PlanCard
import com.phonecalltrue.app.ui.components.PrimaryButton
import com.phonecalltrue.app.ui.components.TextActionButton
import com.phonecalltrue.app.ui.theme.AppDimens
import com.phonecalltrue.app.ui.theme.PremiumGold

@Composable
fun NoAdsScreen(viewModel: AppViewModel, premiumActive: Boolean) {
    var selectedPlan by remember { mutableStateOf("yearly") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(AppDimens.spaceL),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(PremiumGold, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.WorkspacePremium, contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp))
        }
        Text(
            text = "Upgrade to Pro",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = AppDimens.spaceM)
        )
        Text(
            text = "Get access to all our features",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (premiumActive) {
            Text(
                text = "You're on Pro — enjoy an ad-free experience!",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = AppDimens.spaceXL)
            )
            return@Column
        }

        Column(modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spaceL)) {
            listOf(
                Icons.Filled.PersonSearch to "See who is calling even before they call",
                Icons.Filled.PhoneDisabled to "Advanced call blocking options",
                Icons.Filled.Backup to "Backup/Restore Contact",
                Icons.Filled.Lock to "Unlock all features",
                Icons.Filled.VolumeOff to "No ads interruption",
                Icons.Filled.Headset to "24/7 customer support"
            ).forEach { (icon, feature) ->
                Row(modifier = Modifier.padding(vertical = 6.dp)) {
                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(20.dp))
                    Text(feature, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = AppDimens.spaceS))
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spaceL)) {
            PlanCard(
                title = "Monthly Plan",
                price = "₹430.00/Month",
                selected = selectedPlan == "monthly",
                onClick = { selectedPlan = "monthly" }
            )
            PlanCard(
                title = "Yearly Plan",
                price = "₹2,400.00/Year",
                selected = selectedPlan == "yearly",
                onClick = { selectedPlan = "yearly" },
                highlight = true,
                modifier = Modifier.padding(top = AppDimens.spaceS)
            )
        }

        PrimaryButton(
            text = "Try Now",
            onClick = { viewModel.setPremiumActive(true) },
            modifier = Modifier.padding(top = AppDimens.spaceL)
        )
        Text(
            text = "Subscription renews automatically unless cancelled.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = AppDimens.spaceS)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = AppDimens.spaceXS)
        ) {
            Icon(Icons.Filled.Shield, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(16.dp))
            Text(
                text = "Cancel anytime. Secure with Play Store",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = AppDimens.spaceXS)
            )
        }

        Row(modifier = Modifier.padding(top = AppDimens.spaceM, bottom = AppDimens.spaceL)) {
            TextActionButton(text = "Restore", onClick = {})
            TextActionButton(text = "Terms & conditions", onClick = {})
            TextActionButton(text = "Privacy Policy", onClick = {})
        }
    }
}
