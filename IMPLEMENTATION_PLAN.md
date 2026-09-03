# Implementation Plan — Phone Call True

## Status: core app built, compiling, and verified running on-device

58 Kotlin files, clean-architecture layout (`data/`, `navigation/`, `presentation/`, `ui/`), Gradle project builds with `./gradlew.bat assembleDebug` and has been installed + driven end-to-end on a real emulator (AVD `yash_phone`, the same device used to record the reference video).

## What was done, phase by phase

1. **Video analysis** — 98 frames sampled from the 3:16 reference video, reviewed as 5 contact sheets. Produced [SCREEN_INVENTORY.md](SCREEN_INVENTORY.md), [FLOW_MAP.md](FLOW_MAP.md), [COMPONENT_MAP.md](COMPONENT_MAP.md), [DATA_MODEL.md](DATA_MODEL.md).
2. **Project scaffold** — Gradle 8.7 / AGP 8.5.2 / Kotlin 1.9.24 / Compose BOM 2024.06.00, using the toolchain already cached on this machine (no full internet dependency for rebuilds). Wrapper generated, `local.properties` pointed at the installed SDK.
3. **Theme system** — `ui/theme/{Color,Dimens,Shape,Type,Theme}.kt`, light/dark Material3 color schemes matching the video's blue/white/near-black palette, `PhoneCallTrueTheme(themeMode)` driving both Compose colors and system bar appearance.
4. **Data layer** — `data/model/Models.kt` (domain types), `data/local/MockDataSource.kt` (seeded, realistic generators), `data/local/UserPreferencesRepository.kt` (DataStore), `data/local/BlockedNumberDb.kt` (Room), `data/repository/AppRepository.kt` (single repository facade), `AppViewModel.kt` (app-level ViewModel exposing everything as `StateFlow`).
5. **Onboarding** — Splash → Enhanced Caller ID intro → Privacy ("Your comfort comes first!", triggers a **real** `ACCESS_FINE_LOCATION`/`ACCESS_COARSE_LOCATION` runtime request) → Language → Identified Numbers empty state → Background execution explainer → Default Caller ID/Spam app picker (uses the real `RoleManager.ROLE_CALL_SCREENING` API on API 29+).
6. **Main app** — `MainScreen` (drawer + bottom nav + FAB + nested NavHost for the 4 tabs), Recents, Contacts, No Ads/Upgrade, Region (+ city drill-down), Search, Dial Pad, Caller Details.
7. **Drawer destinations** — Call Blocking (Room-backed, add/remove), Backup & Restore (mock, with snackbar confirmation), Settings tree: Language, App Theme, Account, Privacy Policy, Privacy Setting, Feedback, About.
8. **Dark mode** — verified live on-device: toggling in Settings re-themes the whole app instantly, no restart, system bars flip too.
9. **On-device QA** — installed on the `yash_phone` AVD, drove the flow with `uiautomator`-verified taps: full onboarding (including the real Android location-permission dialog), Recents/Contacts/drawer, live dark-mode switch.

## Bugs found and fixed during on-device testing
1. **`androidx.compose.foundation.layout.weight` invalid import** in two settings/onboarding files — broke compilation; removed (it's a scope-member extension, not a top-level import).
2. **Edge-to-edge clipping** — `enableEdgeToEdge()` without inset padding caused the bottom system nav bar to overlap the ACCEPT/DECLINE buttons on every full-bleed screen. Removed edge-to-edge; system bars now reserve their own space as in the reference video.
3. **Onboarding mock-call card** — the phone-icon badge was centered over the card text instead of sitting on the card's bottom edge. Fixed with `Alignment.BottomCenter` + offset.
4. **Persistent drawer sliver** — `ModalDrawerSheet(Modifier.fillMaxSize())` left a permanent ~40dp strip of the drawer visible at rest on every screen. Fixed by giving the sheet an explicit `Modifier.width(300.dp)` instead of `fillMaxSize()`.
5. **Theme/default-dialer radio rows not fully clickable** — only the small `RadioButton` circle was tappable; the label text did nothing. Fixed by moving the click handler onto the row via `Modifier.selectable(...)` and setting `RadioButton(onClick = null)`, matching the pattern already used in `LanguageList`.

## Build & run

```bash
cd "caller id"
./gradlew.bat assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`. Install with `adb install -r app-debug.apk`, launch with `adb shell am start -n com.phonecalltrue.app/.MainActivity`.

## What's implemented vs. what's templated but not yet hand-verified on-device
Verified live on-device: onboarding (all steps incl. real permission + role-request dialogs), Recents, Contacts, drawer, Settings → App Theme (dark-mode live switch).

Built and code-reviewed, not yet individually driven on-device this session: No Ads/Upgrade, Region (+ detail), Search, Dial Pad, Caller Details, Call Blocking, Backup, Language settings, Account, Privacy Policy, Privacy Setting, Feedback, About. These reuse the exact same component set (`SettingsItem`, `EmptyState`, `ConfirmationDialog`, `ToggleSettingItem`, list rows) already exercised and fixed above, so risk is low, but they haven't each been tapped through individually.

## Known Android/platform limitations (by design, per the brief's "don't fake system UI" rule)
- Dialer/call-log/contacts data is generated mock data, not read from `ContentResolver` — see [DATA_MODEL.md](DATA_MODEL.md) "Replacing mock data" for the swap-in path.
- "Call" actions show a confirmation dialog rather than placing a real `ACTION_CALL` (which requires `CALL_PHONE` and would place a real charge); wiring real `ACTION_DIAL`/`ACTION_CALL` is a one-line change in `DialPadScreen`/`CallerDetailsScreen`.
- The default caller-ID/spam-app picker calls the real `RoleManager.ROLE_CALL_SCREENING` API where available; there's no way to impersonate the OS's actual default-app resolver, so on devices/emulators where the role isn't available it falls through to the app-owned picker UI only (never claims to be system Settings).
- Subscription/premium is a local mock flag (`DataStore`), no real billing integration.

## Remaining TODOs for a fully polished release
- Individually drive-test the screens listed as "not yet hand-verified" above.
- Wire `READ_CALL_LOG`/`READ_CONTACTS` real data behind the existing repository interface once device permissions are meant to be exercised for real (currently requested in the manifest but the mock generators are used regardless of grant state, since the video's own data is clearly demo content).
- Unit tests (call-record rendering, phone-number normalization, DataStore persistence) — architecture supports this (`AppRepository`/`PhoneNumberUtils` are pure/injectable) but no test files were written yet.
- App icon is a simple vector placeholder, not a pixel-matched recreation of the reference's icon.
