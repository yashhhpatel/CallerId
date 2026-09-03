# Data Model — Phone Call True

## Domain models (`data/model/Models.kt`)

```kotlin
enum class CallType { INCOMING, OUTGOING, MISSED, BLOCKED }

enum class SpamCategory(val label: String) {
    SAFE, UNKNOWN, TELEMARKETING, SPAM, FRAUD, ROBOCALL, BUSINESS, PERSONAL
}

data class CallRecord(
    val id: String, val name: String?, val phoneNumber: String,
    val avatarColorSeed: Int, val timeLabel: String, val dateGroup: String,
    val callType: CallType, val spamCategory: SpamCategory,
    val durationSeconds: Int, val reportCount: Int
)

data class Contact(
    val id: String, val name: String, val phoneNumber: String,
    val avatarColorSeed: Int, val isFavorite: Boolean
)

data class RegionStat(val id: String, val name: String, val count: Int)

data class SearchResult(
    val phoneNumber: String, val name: String?, val category: SpamCategory,
    val region: String, val reportCount: Int
)

data class BlockedNumber(val id: String, val phoneNumber: String, val name: String?, val reason: String)

data class AppLanguage(val code: String, val displayName: String, val flagEmoji: String)

enum class ThemeMode { SYSTEM, LIGHT, DARK }
```

## Persistence layers

| Data | Store | Why |
|---|---|---|
| Onboarding completed, language code, theme mode, premium flag, privacy toggles (5), rate-us dismissed | **DataStore Preferences** (`UserPreferencesRepository`) | Small key-value flags, read on every app start, need to survive process death |
| Blocked numbers | **Room** (`AppDatabase` → `BlockedNumberEntity`/`BlockedNumberDao`) | Structured, growable list the user actively edits (add/remove) — the one dataset in the brief explicitly worth a real relational store |
| Calls, Contacts, Region stats, Search history | **In-memory `StateFlow`** seeded from `MockDataSource`, held in `AppRepository` (process-scoped singleton) | Large generated datasets that stand in for `ContentResolver`/`CallLog` and a future backend — see "Replacing mock data" below. Search history is intentionally in-memory-only per session; swap for DataStore/Room if cross-session history is desired. |

## Mock data generation (`data/local/MockDataSource.kt`)
Deterministic (seeded `Random`) so re-runs are stable within a process:
- `generateCallRecords(30)` — mixed call types, ~65% named/35% unknown, spam categories skewed toward named=PERSONAL
- `generateContacts(30)` — alphabetically sorted, ~20% marked favorite
- `generateRegionStats()` — the exact India/Gujarat/Orissa/... counts observed in the reference video
- `regionCityBreakdown(regionId)` — 8 pseudo-random Gujarat cities per region, for the drill-down screen
- `generateBlockedNumbers(20)`, `generateSearchHistory(10)`

## Repository surface (`data/repository/AppRepository.kt`)
Single app-scoped repository (`AppRepository.getInstance(context)`) exposing:
- `calls`, `contacts`, `regions`, `searchHistory` — `StateFlow`s
- `blockedNumbers` — `Flow` backed by Room (`observeAllModels()`)
- `lookupNumber(query)` — checks contacts → calls → falls back to a deterministic pseudo-random classification for any 10-digit number, so **every** search query returns a stable, realistic result
- `blockNumber` / `unblockNumber`, `addSearchHistoryEntry` / `clearSearchHistory`
- Delegates preference reads/writes to `UserPreferencesRepository`

`AppViewModel` is the single app-level ViewModel (`AndroidViewModel`) that exposes all of the above as `StateFlow`s via `stateIn(viewModelScope, SharingStarted.Eagerly, ...)`, so every screen reads from one composition root instead of standing up per-screen ViewModels for what is fundamentally shared state.

## Replacing mock data with a real backend
1. Keep `AppRepository`'s public surface (`StateFlow<List<CallRecord>>` etc.) unchanged.
2. Swap `MockDataSource.generateCallRecords()` for a `ContentResolver` query against `CallLog.Calls` (requires `READ_CALL_LOG`, already declared in the manifest).
3. Swap `generateContacts()` for a `ContentResolver` query against `ContactsContract.Contacts` (requires `READ_CONTACTS`, already declared).
4. Replace `lookupNumber()`'s local heuristic with a real caller-ID/spam API call (suspend function + Retrofit/Ktor client), keeping the `SearchResult` shape.
5. Replace `regionCityBreakdown`/`generateRegionStats` with an aggregation endpoint keyed by the same `RegionStat` shape.
6. `BlockedNumber` already round-trips through Room — point the same DAO at a synced backend via a `WorkManager` sync job if multi-device blocking sync is needed later.
