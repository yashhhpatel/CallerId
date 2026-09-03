package com.phonecalltrue.app.data.model

enum class CallType { INCOMING, OUTGOING, MISSED, BLOCKED }

enum class SpamCategory(val label: String) {
    SAFE("Safe"),
    UNKNOWN("Unknown"),
    TELEMARKETING("Telemarketing"),
    SPAM("Spam"),
    FRAUD("Fraud"),
    ROBOCALL("Robocall"),
    BUSINESS("Business"),
    PERSONAL("Personal")
}

data class CallRecord(
    val id: String,
    val name: String?,
    val phoneNumber: String,
    val avatarColorSeed: Int,
    val timeLabel: String,
    val dateGroup: String,
    val callType: CallType,
    val spamCategory: SpamCategory,
    val durationSeconds: Int = 0,
    val reportCount: Int = 0
)

data class Contact(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val avatarColorSeed: Int,
    val isFavorite: Boolean = false
)

data class RegionStat(
    val id: String,
    val name: String,
    val count: Int
)

data class SearchResult(
    val phoneNumber: String,
    val name: String?,
    val category: SpamCategory,
    val region: String,
    val reportCount: Int
)

data class BlockedNumber(
    val id: String,
    val phoneNumber: String,
    val name: String?,
    val reason: String
)

data class AppLanguage(
    val code: String,
    val displayName: String,
    val flagEmoji: String
)

enum class ThemeMode { SYSTEM, LIGHT, DARK }

val AVAILABLE_LANGUAGES = listOf(
    AppLanguage("en", "English", "🇬🇧"),
    AppLanguage("zh", "Chinese", "🇨🇳"),
    AppLanguage("hi", "Hindi", "🇮🇳"),
    AppLanguage("bn", "Bengali", "🇧🇩"),
    AppLanguage("ru", "Russian", "🇷🇺"),
    AppLanguage("pt", "Portuguese", "🇵🇹"),
    AppLanguage("fr", "French", "🇫🇷"),
    AppLanguage("ar", "Arabic", "🇦🇪"),
    AppLanguage("it", "Italian", "🇮🇹")
)
