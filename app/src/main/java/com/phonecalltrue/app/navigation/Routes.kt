package com.phonecalltrue.app.navigation

object Routes {
    const val SPLASH = "splash"

    // Onboarding
    const val ONBOARDING_CALLER_ID = "onboarding/caller_id"
    const val ONBOARDING_PRIVACY = "onboarding/privacy"
    const val ONBOARDING_LANGUAGE = "onboarding/language"
    const val ONBOARDING_BACKGROUND = "onboarding/background"
    const val ONBOARDING_DEFAULT_DIALER = "onboarding/default_dialer"

    // Main
    const val MAIN = "main"
    const val RECENTS = "recents"
    const val CONTACTS = "contacts"
    const val NO_ADS = "no_ads"
    const val REGION = "region"
    const val REGION_DETAIL = "region/{regionId}/{regionName}"
    fun regionDetail(regionId: String, regionName: String) = "region/$regionId/$regionName"

    const val IDENTIFIED_NUMBERS = "identified_numbers"
    const val SEARCH = "search"
    const val CALLER_DETAILS = "caller_details/{phoneNumber}"
    fun callerDetails(phoneNumber: String) = "caller_details/${java.net.URLEncoder.encode(phoneNumber, "UTF-8")}"
    const val DIAL_PAD = "dial_pad"

    // Drawer
    const val CALL_BLOCKING = "call_blocking"
    const val BACKUP = "backup"
    const val SETTINGS = "settings"
    const val LANGUAGE_SETTINGS = "language_settings"
    const val THEME_SETTINGS = "theme_settings"
    const val ACCOUNT = "account"
    const val PRIVACY_POLICY = "privacy_policy"
    const val PRIVACY_SETTINGS = "privacy_settings"
    const val FEEDBACK = "feedback"
    const val ABOUT = "about"
}
