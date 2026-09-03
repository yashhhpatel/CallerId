package com.phonecalltrue.app.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.phonecalltrue.app.data.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "phone_call_true_prefs")

class UserPreferencesRepository(private val context: Context) {

    private object Keys {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val LANGUAGE_CODE = stringPreferencesKey("language_code")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val PREMIUM_ACTIVE = booleanPreferencesKey("premium_active")
        val LOCATION_PERMISSION_ACKED = booleanPreferencesKey("location_permission_acked")
        val PERSONALIZED_ADS = booleanPreferencesKey("privacy_personalized_ads")
        val ANALYTICS = booleanPreferencesKey("privacy_analytics")
        val DATA_SHARING = booleanPreferencesKey("privacy_data_sharing")
        val LOCATION_SHARING = booleanPreferencesKey("privacy_location_sharing")
        val CALLER_ID_ENABLED = booleanPreferencesKey("privacy_caller_id_enabled")
        val RATE_US_DISMISSED = booleanPreferencesKey("rate_us_dismissed")
    }

    val onboardingCompleted: Flow<Boolean> =
        context.dataStore.data.map { it[Keys.ONBOARDING_COMPLETED] ?: false }

    val languageCode: Flow<String> =
        context.dataStore.data.map { it[Keys.LANGUAGE_CODE] ?: "en" }

    val themeMode: Flow<ThemeMode> =
        context.dataStore.data.map { prefs ->
            when (prefs[Keys.THEME_MODE]) {
                "LIGHT" -> ThemeMode.LIGHT
                "DARK" -> ThemeMode.DARK
                else -> ThemeMode.SYSTEM
            }
        }

    val premiumActive: Flow<Boolean> =
        context.dataStore.data.map { it[Keys.PREMIUM_ACTIVE] ?: false }

    val personalizedAds: Flow<Boolean> = context.dataStore.data.map { it[Keys.PERSONALIZED_ADS] ?: true }
    val analyticsEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.ANALYTICS] ?: true }
    val dataSharingEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.DATA_SHARING] ?: false }
    val locationSharingEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.LOCATION_SHARING] ?: true }
    val callerIdEnabled: Flow<Boolean> = context.dataStore.data.map { it[Keys.CALLER_ID_ENABLED] ?: true }
    val rateUsDismissed: Flow<Boolean> = context.dataStore.data.map { it[Keys.RATE_US_DISMISSED] ?: false }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { it[Keys.ONBOARDING_COMPLETED] = completed }
    }

    suspend fun setLanguageCode(code: String) {
        context.dataStore.edit { it[Keys.LANGUAGE_CODE] = code }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { it[Keys.THEME_MODE] = mode.name }
    }

    suspend fun setPremiumActive(active: Boolean) {
        context.dataStore.edit { it[Keys.PREMIUM_ACTIVE] = active }
    }

    suspend fun setPrivacyToggle(key: PrivacyToggle, value: Boolean) {
        context.dataStore.edit { prefs ->
            val prefKey = when (key) {
                PrivacyToggle.PERSONALIZED_ADS -> Keys.PERSONALIZED_ADS
                PrivacyToggle.ANALYTICS -> Keys.ANALYTICS
                PrivacyToggle.DATA_SHARING -> Keys.DATA_SHARING
                PrivacyToggle.LOCATION_SHARING -> Keys.LOCATION_SHARING
                PrivacyToggle.CALLER_ID -> Keys.CALLER_ID_ENABLED
            }
            prefs[prefKey] = value
        }
    }

    suspend fun setRateUsDismissed(dismissed: Boolean) {
        context.dataStore.edit { it[Keys.RATE_US_DISMISSED] = dismissed }
    }
}

enum class PrivacyToggle { PERSONALIZED_ADS, ANALYTICS, DATA_SHARING, LOCATION_SHARING, CALLER_ID }
