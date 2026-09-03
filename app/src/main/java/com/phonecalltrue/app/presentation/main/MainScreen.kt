package com.phonecalltrue.app.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
import com.phonecalltrue.app.ui.components.AdBanner
import com.phonecalltrue.app.ui.components.AppDrawerContent
import com.phonecalltrue.app.ui.components.BottomNavigationBar
import com.phonecalltrue.app.ui.components.BottomTab
import com.phonecalltrue.app.ui.components.FloatingDialPadButton
import com.phonecalltrue.app.ui.components.HomeTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(outerNavController: NavHostController, viewModel: AppViewModel) {
    val innerNavController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val backStackEntry by innerNavController.currentBackStackEntryAsState()
    val currentTab = BottomTab.entries.firstOrNull { it.route == backStackEntry?.destination?.route } ?: BottomTab.RECENTS

    val premiumActive by viewModel.premiumActive.collectAsStateWithLifecycle()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                onCallBlocking = { scope.launch { drawerState.close() }; outerNavController.navigate(Routes.CALL_BLOCKING) },
                onBackup = { scope.launch { drawerState.close() }; outerNavController.navigate(Routes.BACKUP) },
                onShareCallerId = { scope.launch { drawerState.close() } },
                onRateUs = { scope.launch { drawerState.close() } },
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
                if (currentTab != BottomTab.NO_ADS) {
                    FloatingDialPadButton(onClick = { outerNavController.navigate(Routes.DIAL_PAD) })
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
}
