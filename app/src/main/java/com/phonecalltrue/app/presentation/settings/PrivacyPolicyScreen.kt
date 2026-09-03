package com.phonecalltrue.app.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.phonecalltrue.app.ui.components.DetailTopBar
import com.phonecalltrue.app.ui.theme.AppDimens

private val sections = listOf(
    "1. Interpretation and Definitions" to "Words with capitalized initial letters have meanings defined under these conditions, which apply whether they appear in singular or plural form.",
    "2. Data Collection and Usage Practices" to "We collect caller-related identifiers, device information, and approximate location to power caller identification and spam detection.",
    "3. Why We Collect Data" to "Data is used to identify unknown callers, detect spam and fraud patterns, and improve the accuracy of our community-reported database.",
    "4. Your Privacy Controls" to "You can review and adjust what is collected at any time from Settings > Privacy Setting, including location sharing and personalized ads.",
    "5. Sharing Information" to "We may share aggregated, non-identifying information with analytics and advertising partners strictly to operate and fund the free tier of the app.",
    "6. Legal Basis for Processing" to "We process personal data based on consent, performance of a contract, legitimate interests, and legal obligations where applicable.",
    "7. EEA, UK, and Swiss Privacy Rights" to "Residents of the EEA, UK, and Switzerland have rights to access, correct, delete, and port their personal data, and to object to certain processing.",
    "8. U.S. Privacy Disclosures" to "Depending on your state, you may have rights to know, access, delete, or opt out of the sale or sharing of personal information.",
    "9. Refunds" to "Subscription refund requests are handled according to the policies of the app store through which you purchased Phone Call True Pro.",
    "10. Advertising Services" to "Free-tier users may see banner advertisements. You can remove ads entirely by upgrading to Pro from the No Ads screen.",
    "11. Third-Party Services" to "The app relies on third-party infrastructure (cloud storage, analytics, ad mediation) that maintain their own privacy practices.",
    "12. Changes to This Privacy Policy" to "We may update this policy periodically. Material changes will be highlighted with an in-app notice before they take effect.",
    "13. Contact Us" to "Questions about this policy can be sent to privacy@phonecalltrue.app."
)

@Composable
fun PrivacyPolicyScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        DetailTopBar(title = "Privacy Policy", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(AppDimens.spaceL)
        ) {
            Text(
                text = "Effective date: Jan 01, 2026 · Last updated: Jan 01, 2026",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            sections.forEach { (title, body) ->
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth().padding(top = AppDimens.spaceL)
                )
                Text(
                    text = body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AppDimens.spaceXS)
                )
            }
        }
    }
}
