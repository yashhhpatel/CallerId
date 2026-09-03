package com.phonecalltrue.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.phonecalltrue.app.AppViewModel
import com.phonecalltrue.app.presentation.backup.BackupScreen
import com.phonecalltrue.app.presentation.blocking.CallBlockingScreen
import com.phonecalltrue.app.presentation.caller.CallerDetailsScreen
import com.phonecalltrue.app.presentation.dialpad.DialPadScreen
import com.phonecalltrue.app.presentation.main.MainScreen
import com.phonecalltrue.app.presentation.onboarding.BackgroundExecutionScreen
import com.phonecalltrue.app.presentation.onboarding.CallerIdIntroScreen
import com.phonecalltrue.app.presentation.onboarding.DefaultDialerScreen
import com.phonecalltrue.app.presentation.onboarding.IdentifiedNumbersOnboardingScreen
import com.phonecalltrue.app.presentation.onboarding.LanguageOnboardingScreen
import com.phonecalltrue.app.presentation.onboarding.PrivacyOnboardingScreen
import com.phonecalltrue.app.presentation.onboarding.SplashScreen
import com.phonecalltrue.app.presentation.region.RegionDetailScreen
import com.phonecalltrue.app.presentation.search.SearchScreen
import com.phonecalltrue.app.presentation.settings.AboutScreen
import com.phonecalltrue.app.presentation.settings.AccountScreen
import com.phonecalltrue.app.presentation.settings.FeedbackScreen
import com.phonecalltrue.app.presentation.settings.LanguageSettingsScreen
import com.phonecalltrue.app.presentation.settings.PrivacyPolicyScreen
import com.phonecalltrue.app.presentation.settings.PrivacySettingsScreen
import com.phonecalltrue.app.presentation.settings.SettingsScreen
import com.phonecalltrue.app.presentation.settings.ThemeSettingsScreen
import java.net.URLDecoder

@Composable
fun PhoneCallTrueNavHost(viewModel: AppViewModel) {
    val navController = rememberNavController()
    val languageCode by viewModel.languageCode.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            val onboardingCompleted by viewModel.onboardingCompleted.collectAsStateWithLifecycle()
            SplashScreen(
                onFinished = {
                    val destination = if (onboardingCompleted == true) Routes.MAIN else Routes.ONBOARDING_CALLER_ID
                    navController.navigate(destination) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.ONBOARDING_CALLER_ID) {
            CallerIdIntroScreen(
                onAccept = { navController.navigate(Routes.ONBOARDING_PRIVACY) },
                onDecline = { navController.navigate(Routes.MAIN) { popUpTo(0) } }
            )
        }
        composable(Routes.ONBOARDING_PRIVACY) {
            PrivacyOnboardingScreen(
                onAccept = { navController.navigate(Routes.ONBOARDING_LANGUAGE) },
                onDecline = { navController.navigate(Routes.ONBOARDING_LANGUAGE) }
            )
        }
        composable(Routes.ONBOARDING_LANGUAGE) {
            LanguageOnboardingScreen(
                selectedCode = languageCode,
                onLanguageSelected = { viewModel.setLanguageCode(it) },
                onConfirm = { navController.navigate("identified_numbers_onboarding") }
            )
        }
        composable("identified_numbers_onboarding") {
            IdentifiedNumbersOnboardingScreen(
                onGetStarted = { navController.navigate(Routes.ONBOARDING_BACKGROUND) },
                onSkip = { navController.navigate(Routes.ONBOARDING_BACKGROUND) }
            )
        }
        composable(Routes.ONBOARDING_BACKGROUND) {
            BackgroundExecutionScreen(
                onAllow = { navController.navigate(Routes.ONBOARDING_DEFAULT_DIALER) },
                onDecline = { navController.navigate(Routes.ONBOARDING_DEFAULT_DIALER) }
            )
        }
        composable(Routes.ONBOARDING_DEFAULT_DIALER) {
            DefaultDialerScreen(
                onFinished = {
                    viewModel.completeOnboarding()
                    navController.navigate(Routes.MAIN) { popUpTo(0) }
                }
            )
        }

        composable(Routes.MAIN) {
            MainScreen(outerNavController = navController, viewModel = viewModel)
        }

        composable(
            Routes.REGION_DETAIL,
            arguments = listOf(navArgument("regionId") { type = NavType.StringType }, navArgument("regionName") { type = NavType.StringType })
        ) { entry ->
            RegionDetailScreen(
                viewModel = viewModel,
                regionId = entry.arguments?.getString("regionId").orEmpty(),
                regionName = entry.arguments?.getString("regionName").orEmpty(),
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SEARCH) {
            SearchScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onResultClick = { phoneNumber -> navController.navigate(Routes.callerDetails(phoneNumber)) }
            )
        }

        composable(
            Routes.CALLER_DETAILS,
            arguments = listOf(navArgument("phoneNumber") { type = NavType.StringType })
        ) { entry ->
            val encoded = entry.arguments?.getString("phoneNumber").orEmpty()
            CallerDetailsScreen(
                viewModel = viewModel,
                phoneNumber = URLDecoder.decode(encoded, "UTF-8"),
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.DIAL_PAD) {
            DialPadScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.CALL_BLOCKING) {
            CallBlockingScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.BACKUP) {
            BackupScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onLanguage = { navController.navigate(Routes.LANGUAGE_SETTINGS) },
                onTheme = { navController.navigate(Routes.THEME_SETTINGS) },
                onAccount = { navController.navigate(Routes.ACCOUNT) },
                onPrivacyPolicy = { navController.navigate(Routes.PRIVACY_POLICY) },
                onPrivacySettings = { navController.navigate(Routes.PRIVACY_SETTINGS) },
                onFeedback = { navController.navigate(Routes.FEEDBACK) },
                onAbout = { navController.navigate(Routes.ABOUT) }
            )
        }
        composable(Routes.LANGUAGE_SETTINGS) {
            LanguageSettingsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.THEME_SETTINGS) {
            ThemeSettingsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.ACCOUNT) {
            AccountScreen(userEmail = "pdarshil996@gmail.com", onBack = { navController.popBackStack() })
        }
        composable(Routes.PRIVACY_POLICY) {
            PrivacyPolicyScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.PRIVACY_SETTINGS) {
            PrivacySettingsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.FEEDBACK) {
            FeedbackScreen(userEmail = "pdarshil996@gmail.com", onBack = { navController.popBackStack() })
        }
        composable(Routes.ABOUT) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
    }
}
