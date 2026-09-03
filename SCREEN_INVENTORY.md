# Screen Inventory — Phone Call True

Derived from frame-by-frame analysis of the reference video (196s, 98 sampled frames).

## Onboarding / First-run
1. Splash — app icon animation, brand name
2. Enhanced Caller ID intro — mock incoming-call preview card, ACCEPT/DECLINE
3. Choose an account (Google account picker look-alike, app-owned UI, not a real system chooser)
4. Display over other apps permission explainer — TURN ON / DECLINE
5. "Continue without Enhanced Caller ID?" confirm dialog — Cancel / Skip
6. "Your comfort comes first!" privacy explainer (bulleted list) — ACCEPT/DECLINE
7. Location permission rationale + real Android location permission request
8. Background execution explainer ("Let app always run in background?") — Decline/Allow
9. Languages screen (first-run variant, checkmark confirm top-right)
10. Call log / Contacts / Notifications permission explainers (real Android permissions, app-styled rationale first)
11. Identified Numbers empty state ("Identifying… Please wait" → "No Contacts Found" → Get Started)
12. Default Caller ID & Spam app selector (None / Phone Call True / Phone / Truecaller) — Cancel / Set as default

## Main app
13. Recents (home) — top bar (hamburger, title, search), "Today"/"Yesterday" sections, call rows, FAB dial pad, bottom nav, ad banner
14. Contacts — search field, "Your Favorites" edit row, alphabetical index rail, contact rows, FAB
15. No Ads / Upgrade to Pro — feature bullet list, Monthly/Yearly plan cards, Restore/Terms/Privacy links
16. Region — "India (1150)" header, per-state rows with count + letter avatar, drill-down to city-level list, country grouping (Germany/UK/Other) on search variant
17. Search Phone Number — search bar, idle "No recent searches" state, numeric keypad overlay
18. Dial pad — 12-key grid, backspace, call button
19. Call Blocking — list, "Click on + icon to block contact", FAB add
20. Backup & Restore — Backup card w/ last-backup date + Backup button, Restore card + Restore button, Google Drive account section
21. Caller details (implied by call rows / block-contact flow — name, number, call/message/block actions)

## Drawer & Settings tree
22. Navigation drawer — header (icon + app name), Call Blocking, Backup, Share Caller ID, Rate Us, Settings, version footer
23. Settings — General (Language, App Theme, Account), Privacy (Privacy Policy, Privacy Setting), Others (Feedback, About)
24. Language settings — flag + name rows, single-select radio, top-right check
25. App Theme — System / Dark Mode / Bright Mode radio group
26. Account — avatar, name, email, Log out, Linked Account (Google, Connected), Delete Account (destructive, red)
27. Privacy Policy — numbered sections, scrollable long-form
28. Privacy Setting — Location Permission toggle (+ room for more toggles per spec)
29. Feedback — not directly captured on video; build per spec (type, message, email, submit)
30. About — app icon, name, tagline, version footer

## System-adjacent (rendered as app-owned, never fake OS chrome)
31. "5% battery remaining… Turn on Super power saving mode?" — OS dialog observed in passing, NOT part of our app; ignored for implementation
32. Play Store listing mock (external) — ignored, not part of our app
33. Email compose (support mail) — ignored, not part of our app

## Ad placement
- Bottom banner strip on Recents/Contacts/Region/Search/Drawer screens ("Try Now" + sponsored app card) → implemented as replaceable `AdBanner` composable, not a real ad SDK.

Total in-app screens to build: **30** (13 onboarding/permission steps + 9 main/tool screens + 8 drawer/settings screens), matching the spec's route list in section 49.
