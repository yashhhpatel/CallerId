package com.phonecalltrue.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.phonecalltrue.app.data.local.PrivacyToggle
import com.phonecalltrue.app.data.model.BlockedNumber
import com.phonecalltrue.app.data.model.ThemeMode
import com.phonecalltrue.app.data.repository.AppRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    val repository = AppRepository.getInstance(application)

    val onboardingCompleted: StateFlow<Boolean?> = repository.prefs.onboardingCompleted
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val themeMode: StateFlow<ThemeMode> = repository.prefs.themeMode
        .stateIn(viewModelScope, SharingStarted.Eagerly, ThemeMode.SYSTEM)

    val languageCode: StateFlow<String> = repository.prefs.languageCode
        .stateIn(viewModelScope, SharingStarted.Eagerly, "en")

    val premiumActive: StateFlow<Boolean> = repository.prefs.premiumActive
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val calls = repository.calls
    val contacts = repository.contacts
    val regions = repository.regions
    val searchHistory = repository.searchHistory
    val blockedNumbers: StateFlow<List<BlockedNumber>> = repository.blockedNumbers
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        viewModelScope.launch { repository.ensureBlockedNumbersSeeded() }
    }

    fun completeOnboarding() = viewModelScope.launch { repository.setOnboardingCompleted(true) }
    fun setThemeMode(mode: ThemeMode) = viewModelScope.launch { repository.setThemeMode(mode) }
    fun setLanguageCode(code: String) = viewModelScope.launch { repository.setLanguageCode(code) }
    fun setPremiumActive(active: Boolean) = viewModelScope.launch { repository.setPremiumActive(active) }
    fun setPrivacyToggle(key: PrivacyToggle, value: Boolean) = viewModelScope.launch { repository.setPrivacyToggle(key, value) }
    fun setRateUsDismissed(dismissed: Boolean) = viewModelScope.launch { repository.setRateUsDismissed(dismissed) }

    fun addContact(name: String, phoneNumber: String) = repository.addContact(name, phoneNumber)
    fun blockNumber(blocked: BlockedNumber) = viewModelScope.launch { repository.blockNumber(blocked) }
    fun unblockNumber(id: String) = viewModelScope.launch { repository.unblockNumber(id) }

    fun addSearchHistoryEntry(number: String) = repository.addSearchHistoryEntry(number)
    fun clearSearchHistory() = repository.clearSearchHistory()
    fun lookupNumber(query: String) = repository.lookupNumber(query)
    fun regionCities(regionId: String) = repository.regionCities(regionId)
}
