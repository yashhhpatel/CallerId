package com.phonecalltrue.app.presentation.main

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.navigation.Routes
import com.phonecalltrue.app.presentation.contacts.ContactsScreen
import com.phonecalltrue.app.presentation.noads.NoAdsScreen
import com.phonecalltrue.app.presentation.recents.RecentsScreen
import com.phonecalltrue.app.presentation.region.RegionScreen
import com.phonecalltrue.app.ui.components.AddContactFab
import com.phonecalltrue.app.ui.components.AppDrawerContent
import com.phonecalltrue.app.ui.components.BottomNavigationBar
import com.phonecalltrue.app.ui.components.BottomTab
import com.phonecalltrue.app.ui.components.FloatingDialPadButton
import com.phonecalltrue.app.ui.components.HomeTopBar
import com.phonecalltrue.app.ui.components.PhoneSearchField
import com.phonecalltrue.app.ui.components.RateUsDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(outerNavController: NavHostController, viewModel: AppViewModel) {
    val innerNavController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showRateUs by remember { mutableStateOf(false) }
    var showAddContact by remember { mutableStateOf(false) }

    val backStackEntry by innerNavController.currentBackStackEntryAsState()
    val currentTab = BottomTab.entries.firstOrNull { it.route == backStackEntry?.destination?.route } ?: BottomTab.RECENTS

    val premiumActive by viewModel.premiumActive.collectAsStateWithLifecycle()

    // System back should close the drawer when it's open, rather than falling
    // through to the Activity and exiting the app.
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                onCallBlocking = { scope.launch { drawerState.close() }; outerNavController.navigate(Routes.CALL_BLOCKING) },
                onBackup = { scope.launch { drawerState.close() }; outerNavController.navigate(Routes.BACKUP) },
                onShareCallerId = {
                    scope.launch { drawerState.close() }
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "Check out Phone Call True — Enhanced Caller ID & spam protection: https://play.google.com/store/apps/details?id=com.phonecalltrue.app")
                    }
                    context.startActivity(Intent.createChooser(sendIntent, "Share Caller ID"))
                },
                onRateUs = { scope.launch { drawerState.close() }; showRateUs = true },
                onSettings = { scope.launch { drawerState.close() }; outerNavController.navigate(Routes.SETTINGS) }
            )
        }
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    title = "Phone Call True",
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onSearchClick = { outerNavController.navigate(Routes.SEARCH) }
                )
            },
            bottomBar = {
                BottomNavigationBar(selectedTab = currentTab) { tab ->
                    innerNavController.navigate(tab.route) {
                        popUpTo(BottomTab.RECENTS.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            floatingActionButton = {
                when (currentTab) {
                    BottomTab.CONTACTS -> AddContactFab(onClick = { showAddContact = true })
                    BottomTab.NO_ADS -> {}
                    else -> FloatingDialPadButton(onClick = { outerNavController.navigate(Routes.DIAL_PAD) })
                }
            }
        ) { padding ->
            NavHost(
                navController = innerNavController,
                startDestination = BottomTab.RECENTS.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(BottomTab.RECENTS.route) {
                    RecentsScreen(
                        viewModel = viewModel,
                        showAds = !premiumActive,
                        onCallClick = { phoneNumber -> outerNavController.navigate(Routes.callerDetails(phoneNumber)) }
                    )
                }
                composable(BottomTab.CONTACTS.route) {
                    ContactsScreen(
                        viewModel = viewModel,
                        showAds = !premiumActive,
                        onContactClick = { phoneNumber -> outerNavController.navigate(Routes.callerDetails(phoneNumber)) }
                    )
                }
                composable(BottomTab.NO_ADS.route) {
                    NoAdsScreen(viewModel = viewModel, premiumActive = premiumActive)
                }
                composable(BottomTab.REGION.route) {
                    RegionScreen(
                        viewModel = viewModel,
                        showAds = !premiumActive,
                        onRegionClick = { id, name -> outerNavController.navigate(Routes.regionDetail(id, name)) }
                    )
                }
            }
        }
    }

    if (showRateUs) {
        RateUsDialog(
            onRateNow = { _ ->
                showRateUs = false
                viewModel.setRateUsDismissed(true)
                val marketIntent = Intent(
                    Intent.ACTION_VIEW,
                    android.net.Uri.parse("market://details?id=com.phonecalltrue.app")
                )
                runCatching { context.startActivity(marketIntent) }
            },
            onMaybeLater = {
                showRateUs = false
                viewModel.setRateUsDismissed(true)
            }
        )
    }

    if (showAddContact) {
        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showAddContact = false },
            title = { androidx.compose.material3.Text("Add Contact") },
            text = {
                androidx.compose.foundation.layout.Column {
                    androidx.compose.foundation.text.BasicTextField(
                        value = name,
                        onValueChange = { name = it },
                        textStyle = androidx.compose.material3.LocalTextStyle.current.copy(color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface),
                        decorationBox = { inner ->
                            if (name.isEmpty()) androidx.compose.material3.Text("Name", color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant)
                            inner()
                        },
                        modifier = Modifier.padding(bottom = com.phonecalltrue.app.ui.theme.AppDimens.spaceM)
                    )
                    PhoneSearchField(value = phone, onValueChange = { phone = it }, placeholder = "Phone number")
                }
            },
            confirmButton = {
                androidx.compose.material3.TextButton(
                    onClick = {
                        if (name.isNotBlank() && phone.isNotBlank()) {
                            viewModel.addContact(name.trim(), com.phonecalltrue.app.utils.PhoneNumberUtils.displayFormat(phone.trim()))
                            showAddContact = false
                        }
                    }
                ) { androidx.compose.material3.Text("Save") }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(onClick = { showAddContact = false }) { androidx.compose.material3.Text("Cancel") }
            }
        )
    }
}
