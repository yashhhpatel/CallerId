# Flow Map — Phone Call True

```
Splash (900ms fade/scale)
  └─ onboardingCompleted? ──yes──▶ Main
       │no
       ▼
Enhanced Caller ID intro ── DECLINE ──▶ Main (limited mode)
  │ ACCEPT
  ▼
Privacy onboarding ("Your comfort comes first!")
  │ ACCEPT ── requests ACCESS_FINE/COARSE_LOCATION (real OS dialog)
  │ DECLINE ── skip, continue
  ▼
Language onboarding (radio list + confirm check)
  ▼
Identified Numbers (loading → "No Contacts Found" empty state)
  │ Get Started / Skip
  ▼
Background execution explainer ── Allow/Decline ──▶ continue either way
  ▼
Default Caller ID & Spam app picker
  │ "Phone Call True" + Set as default → RoleManager.ROLE_CALL_SCREENING (API 29+, real)
  │ any other option / Cancel → continue
  ▼
Main (onboardingCompleted = true, persisted)
```

## Main app navigation

```
Main (Scaffold: HomeTopBar + BottomNavigationBar + FAB)
 ├─ ☰ Hamburger → ModalNavigationDrawer
 │    ├─ Call Blocking      → full-screen (outer nav)
 │    ├─ Backup              → full-screen (outer nav)
 │    ├─ Share Caller ID     → Android Sharesheet (ACTION_SEND)
 │    ├─ Rate Us             → dialog (5-star, persists dismissal)
 │    └─ Settings            → full-screen (outer nav) → Settings tree
 ├─ 🔍 Search icon → Search Phone Number (full-screen)
 │    └─ result → Caller Details
 ├─ FAB (dial pad icon) → Dial Pad (full-screen)
 │    └─ Call → confirm dialog (demo — no real call placed)
 └─ Bottom tabs (inner NavHost, state preserved across switches)
      ├─ Recents  → tap row → Caller Details
      ├─ Contacts → tap row → Caller Details
      ├─ No Ads   → plan selection → mock premium activation
      └─ Region   → tap state → Region Detail (city breakdown)
```

## Settings tree

```
Settings
 ├─ Language        (persists via DataStore, live-applies)
 ├─ App Theme       (System/Light/Dark, persists, applies without restart)
 ├─ Account         (mock sign-in state, Log out, Delete Account confirm)
 ├─ Privacy Policy  (scrollable long-form)
 ├─ Privacy Setting (5 toggles, each persisted individually)
 ├─ Feedback        (type chips + message + optional email + submit)
 └─ About           (icon, name, version, tagline)
```

## Back-navigation contract
- Drawer open → back closes drawer, does not pop the nav stack.
- Any dialog open → back dismisses dialog only.
- Full-screen destinations (Search, Dial Pad, Caller Details, drawer items, Settings subpages) → back pops to the previous screen via `popBackStack()`.
- Bottom-tab switches use `popUpTo(start) { saveState = true }` + `restoreState = true` so tab state survives switching without growing the back stack.
- Onboarding steps use `navController.navigate(...) { popUpTo(0) }` on terminal transitions (decline / finish) so the user can never navigate back into onboarding once in Main.
