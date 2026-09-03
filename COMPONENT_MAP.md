# Component Map — Phone Call True

All reusable composables live under `ui/theme` (tokens) and `ui/components` (widgets). Screens compose these rather than duplicating layout logic.

## Theme tokens (`ui/theme`)
| File | Purpose |
|---|---|
| `Color.kt` | Light/dark palettes, avatar palette, spam-category accent colors |
| `Dimens.kt` | `AppDimens` — spacing scale, corner radii, avatar/touch-target sizes |
| `Shape.kt` | `AppShapes` — Material3 `Shapes` (6/10/16/20/28dp corners) |
| `Type.kt` | `AppTypography` — full Material3 type scale |
| `Theme.kt` | `PhoneCallTrueTheme` — resolves System/Light/Dark, sets system bars |

## Widgets (`ui/components`)
| Component | Used by |
|---|---|
| `PrimaryButton`, `SecondaryButton`, `TextActionButton` | Onboarding, No Ads, Backup, Feedback, Account, dialogs |
| `HomeTopBar`, `DetailTopBar` | Main scaffold / every full-screen destination |
| `BottomNavigationBar` + `BottomTab` enum | Main scaffold |
| `AppDrawerContent` | Main scaffold (`ModalNavigationDrawer`) |
| `InitialsAvatar` | Call rows, contact rows, region rows, caller details, blocking rows |
| `CallHistoryItem`, `ContactItem`, `RegionItem` | Recents, Contacts, Region(+detail) |
| `SettingsItem`, `ToggleSettingItem` | Settings, Privacy Setting |
| `PhoneSearchField` | Search, Contacts filter, Call Blocking add-number dialog |
| `EmptyState` | Recents, Contacts, Search, Call Blocking, Identified Numbers |
| `AdBanner` | Recents, Contacts, Region (replaceable placeholder — see section 42 of the brief) |
| `ConfirmationDialog`, `PermissionRationaleDialog`, `RateUsDialog` | Dial pad call confirm, Caller Details block confirm, Account delete confirm, Rate Us drawer item |
| `FloatingDialPadButton` | Main scaffold FAB |
| `PlanCard` | No Ads plan selector |
| `SectionHeader` | Recents date groups, Contacts A–Z index, Settings section labels |
| `SpamBadge` / `SpamCategory.badgeColor()` | Caller Details, Search results |
| `LanguageList` | Onboarding language step + Settings > Language (single shared implementation) |
| `Modifier.clickableRow()` | Shared ripple-clickable row modifier used across list items and drawer rows |

## Screens → components they compose
- **CallerIdIntroScreen**: `PrimaryButton`, `SecondaryButton`, inline `MockIncomingCallCard`
- **PrivacyOnboardingScreen**: `PrimaryButton`, `SecondaryButton`, real `ActivityResultContracts.RequestMultiplePermissions`
- **LanguageOnboardingScreen** / **LanguageSettingsScreen**: `LanguageList`
- **IdentifiedNumbersOnboardingScreen**: `EmptyState`, `CircularProgressIndicator`
- **DefaultDialerScreen**: real `RoleManager.ROLE_CALL_SCREENING` request (API 29+) with app-owned fallback UI
- **MainScreen**: `HomeTopBar`, `BottomNavigationBar`, `AppDrawerContent`, `FloatingDialPadButton`, nested `NavHost` for the 4 tabs
- **RecentsScreen / ContactsScreen / RegionScreen**: `SectionHeader`, `CallHistoryItem`/`ContactItem`/`RegionItem`, `EmptyState`, `AdBanner`
- **NoAdsScreen**: `PlanCard`, `PrimaryButton`, `TextActionButton`
- **SearchScreen**: `PhoneSearchField`, `EmptyState`, `SpamBadge`, `InitialsAvatar`
- **DialPadScreen**: custom `DialKey`, `ConfirmationDialog`
- **CallerDetailsScreen**: `InitialsAvatar`, `SpamBadge`, `ConfirmationDialog`
- **CallBlockingScreen**: `EmptyState`, `AlertDialog`-based add-number flow
- **BackupScreen**: custom `BackupCard`, `SnackbarHost`
- **SettingsScreen** and children: `SettingsItem`, `ToggleSettingItem`, `SectionHeader`
