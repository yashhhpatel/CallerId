package com.phonecalltrue.app.data.repository

import android.content.Context
import com.phonecalltrue.app.data.local.AppDatabase
import com.phonecalltrue.app.data.local.MockDataSource
import com.phonecalltrue.app.data.local.PrivacyToggle
import com.phonecalltrue.app.data.local.UserPreferencesRepository
import com.phonecalltrue.app.data.local.observeAllModels
import com.phonecalltrue.app.data.local.toEntity
import com.phonecalltrue.app.data.model.BlockedNumber
import com.phonecalltrue.app.data.model.CallRecord
import com.phonecalltrue.app.data.model.Contact
import com.phonecalltrue.app.data.model.RegionStat
import com.phonecalltrue.app.data.model.SearchResult
import com.phonecalltrue.app.data.model.SpamCategory
import com.phonecalltrue.app.data.model.ThemeMode
import com.phonecalltrue.app.utils.PhoneNumberUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Single app-level repository backing all screens with in-memory mock data
 * (calls/contacts/region) plus durable persistence (DataStore for prefs,
 * Room for blocked numbers). Swap the mock generators for real API/ContentResolver
 * calls when a backend is available — the StateFlow surface stays the same.
 */
class AppRepository(context: Context) {

    val prefs = UserPreferencesRepository(context)
    private val db = AppDatabase.getInstance(context)
    private val blockedDao = db.blockedNumberDao()

    private val _calls = MutableStateFlow(MockDataSource.generateCallRecords())
    val calls: StateFlow<List<CallRecord>> = _calls.asStateFlow()

    private val _contacts = MutableStateFlow(MockDataSource.generateContacts())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    fun addContact(name: String, phoneNumber: String) {
        val contact = Contact(
            id = "contact_${System.currentTimeMillis()}",
            name = name,
            phoneNumber = phoneNumber,
            avatarColorSeed = name.hashCode(),
            isFavorite = false
        )
        _contacts.value = (_contacts.value + contact).sortedBy { it.name }
    }

    private val _regions = MutableStateFlow(MockDataSource.generateRegionStats())
    val regions: StateFlow<List<RegionStat>> = _regions.asStateFlow()

    private val _searchHistory = MutableStateFlow(MockDataSource.generateSearchHistory())
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()

    val blockedNumbers: kotlinx.coroutines.flow.Flow<List<BlockedNumber>> = blockedDao.observeAllModels()

    suspend fun ensureBlockedNumbersSeeded() {
        if (blockedDao.count() == 0) {
            blockedDao.insertAll(MockDataSource.generateBlockedNumbers().map { it.toEntity() })
        }
    }

    suspend fun blockNumber(blocked: BlockedNumber) {
        blockedDao.insert(blocked.toEntity())
    }

    suspend fun unblockNumber(id: String) {
        blockedDao.deleteById(id)
    }

    fun regionCities(regionId: String): List<RegionStat> = MockDataSource.regionCityBreakdown(regionId)

    fun addSearchHistoryEntry(number: String) {
        _searchHistory.value = (listOf(number) + _searchHistory.value.filterNot { it == number }).take(15)
    }

    fun clearSearchHistory() {
        _searchHistory.value = emptyList()
    }

    fun lookupNumber(query: String): SearchResult? {
        val normalized = PhoneNumberUtils.normalize(query)
        if (normalized.length < 5) return null
        val matchedCall = _calls.value.firstOrNull { PhoneNumberUtils.normalize(it.phoneNumber).endsWith(normalized) }
        val matchedContact = _contacts.value.firstOrNull { PhoneNumberUtils.normalize(it.phoneNumber).endsWith(normalized) }
        return when {
            matchedContact != null -> SearchResult(
                phoneNumber = matchedContact.phoneNumber,
                name = matchedContact.name,
                category = SpamCategory.PERSONAL,
                region = "India",
                reportCount = 0
            )
            matchedCall != null -> SearchResult(
                phoneNumber = matchedCall.phoneNumber,
                name = matchedCall.name,
                category = matchedCall.spamCategory,
                region = "India",
                reportCount = matchedCall.reportCount
            )
            normalized.length >= 10 -> {
                val seed = normalized.hashCode()
                val category = SpamCategory.entries.toTypedArray()[Math.floorMod(seed, SpamCategory.entries.size)]
                SearchResult(
                    phoneNumber = PhoneNumberUtils.formatIndian(normalized),
                    name = null,
                    category = category,
                    region = _regions.value.random(kotlin.random.Random(seed)).name,
                    reportCount = if (category == SpamCategory.SPAM || category == SpamCategory.TELEMARKETING) Math.floorMod(seed, 4000) + 20 else 0
                )
            }
            else -> null
        }
    }

    suspend fun setThemeMode(mode: ThemeMode) = prefs.setThemeMode(mode)
    suspend fun setLanguageCode(code: String) = prefs.setLanguageCode(code)
    suspend fun setOnboardingCompleted(completed: Boolean) = prefs.setOnboardingCompleted(completed)
    suspend fun setPremiumActive(active: Boolean) = prefs.setPremiumActive(active)
    suspend fun setPrivacyToggle(key: PrivacyToggle, value: Boolean) = prefs.setPrivacyToggle(key, value)
    suspend fun setRateUsDismissed(dismissed: Boolean) = prefs.setRateUsDismissed(dismissed)

    companion object {
        @Volatile private var INSTANCE: AppRepository? = null
        fun getInstance(context: Context): AppRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppRepository(context.applicationContext).also { INSTANCE = it }
            }
    }
}
