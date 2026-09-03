package com.phonecalltrue.app.presentation.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.phonecalltrue.app.ui.components.EmptyState
import com.phonecalltrue.app.ui.theme.AppDimens
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdentifiedNumbersOnboardingScreen(onGetStarted: () -> Unit, onSkip: () -> Unit) {
    var loading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(900)
        loading = false
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Identified Numbers") },
            actions = { TextButton(onClick = onSkip) { Text("Skip") } },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
        if (loading) {
            Column(
                modifier = Modifier.fillMaxSize().padding(AppDimens.spaceXL),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                Text(
                    "Identifying… Please wait",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AppDimens.spaceM)
                )
            }
        } else {
            EmptyState(
                icon = Icons.Filled.PersonSearch,
                title = "No Contacts Found",
                description = "We couldn't identify any saved contacts yet. You can still search any number manually.",
                ctaLabel = "Get Started",
                onCtaClick = onGetStarted,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
