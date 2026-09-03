package com.phonecalltrue.app.data.local

import com.phonecalltrue.app.data.model.BlockedNumber
import com.phonecalltrue.app.data.model.CallRecord
import com.phonecalltrue.app.data.model.CallType
import com.phonecalltrue.app.data.model.Contact
import com.phonecalltrue.app.data.model.RegionStat
import com.phonecalltrue.app.data.model.SpamCategory
import kotlin.random.Random

/** Deterministic, realistic seed data so every screen renders populated on first launch. */
object MockDataSource {

    private val firstNames = listOf(
        "Yash", "Jerambhai", "Dinesh", "Surbhibhai", "Narayan", "Krishika", "Bhavik", "Chirag",
        "Aarav", "Priya", "Rohan", "Ananya", "Vikram", "Sneha", "Karan", "Meera", "Arjun", "Divya",
        "Sanjay", "Pooja", "Ravi", "Kavita", "Amit", "Nisha", "Deepak", "Rekha", "Manish", "Shreya",
        "Suresh", "Anjali"
    )
    private val lastNames = listOf(
        "Patel", "Sasani", "Baghabh", "Jalawar", "Chatpur", "Kalubhai", "Rajubhai", "Yogesh",
        "Master", "Shankar", "Vajubhai", "Surgaja", "Pavasayalo", "Mangaloriya", "Gujarati",
        "Desai", "Shah", "Mehta", "Joshi", "Trivedi"
    )
    private val cities = listOf(
        "Ahmedabad", "Surat", "Vadodara", "Rajkot", "Bhavnagar", "Jamnagar", "Junagadh", "Gandhinagar",
        "Anand", "Nadiad", "Mehsana", "Morbi", "Bharuch", "Valsad", "Navsari"
    )

    fun randomIndianNumber(seed: Int): String {
        val rng = Random(seed)
        val prefix = listOf("9", "8", "7", "6").random(rng)
        val rest = (1..9).map { rng.nextInt(0, 10) }.joinToString("")
        val full = prefix + rest
        return "+91 ${full.substring(0, 5)} ${full.substring(5)}"
    }

    fun generateCallRecords(count: Int = 30): List<CallRecord> {
        val dateGroups = listOf("Today", "Today", "Today", "Yesterday", "Yesterday", "This Week", "This Week", "Earlier")
        val times = listOf("12:52 AM", "11:09 PM", "10:46 PM", "09:41 PM", "09:31 PM", "08:53 PM", "08:42 AM", "08:58 AM", "06:10 PM", "05:55 PM", "05:09 PM", "04:56 PM", "04:40 PM", "04:15 PM", "04:13 PM")
        return (1..count).map { i ->
            val rng = Random(i * 31)
            val hasName = rng.nextInt(0, 100) < 65
            val name = if (hasName) "${firstNames.random(rng)} ${lastNames.random(rng)}" else null
            val typeRoll = rng.nextInt(0, 100)
            val type = when {
                typeRoll < 45 -> CallType.INCOMING
                typeRoll < 70 -> CallType.OUTGOING
                typeRoll < 90 -> CallType.MISSED
                else -> CallType.BLOCKED
            }
            val category = if (hasName) SpamCategory.PERSONAL else listOf(
                SpamCategory.UNKNOWN, SpamCategory.TELEMARKETING, SpamCategory.SPAM, SpamCategory.ROBOCALL, SpamCategory.BUSINESS
            ).random(rng)
            CallRecord(
                id = "call_$i",
                name = name,
                phoneNumber = randomIndianNumber(i * 7),
                avatarColorSeed = i,
                timeLabel = times[i % times.size],
                dateGroup = dateGroups[i % dateGroups.size],
                callType = type,
                spamCategory = category,
                durationSeconds = rng.nextInt(0, 600),
                reportCount = if (category == SpamCategory.SPAM || category == SpamCategory.TELEMARKETING) rng.nextInt(50, 5000) else 0
            )
        }
    }

    fun generateContacts(count: Int = 30): List<Contact> {
        return (1..count).map { i ->
            val rng = Random(i * 17)
            val name = "${firstNames.random(rng)} ${lastNames.random(rng)}"
            Contact(
                id = "contact_$i",
                name = name,
                phoneNumber = randomIndianNumber(i * 13),
                avatarColorSeed = i,
                isFavorite = rng.nextInt(0, 100) < 20
            )
        }.sortedBy { it.name }
    }

    fun generateRegionStats(): List<RegionStat> = listOf(
        RegionStat("gj", "Gujarat", 766),
        RegionStat("in", "India", 156),
        RegionStat("or", "Orissa", 33),
        RegionStat("mum", "Mumbai", 17),
        RegionStat("mh_ga", "Maharashtra / Goa", 16),
        RegionStat("up_e", "Uttar Pradesh (East)", 13),
        RegionStat("hp", "Himachal Pradesh", 10),
        RegionStat("rj", "Rajasthan", 10),
        RegionStat("mp", "Madhya Pradesh", 9),
        RegionStat("wb", "West Bengal", 8),
        RegionStat("ka", "Karnataka", 7),
        RegionStat("tn", "Tamil Nadu", 6),
        RegionStat("pb", "Punjab", 5),
        RegionStat("as", "Assam", 4),
        RegionStat("dl", "Delhi NCR", 4),
        RegionStat("br", "Bihar", 3),
        RegionStat("hr", "Haryana", 3),
        RegionStat("ker", "Kerala", 3),
        RegionStat("ct", "Chhattisgarh", 2),
        RegionStat("jh", "Jharkhand", 1),
        RegionStat("other", "Other", 74)
    )

    fun regionCityBreakdown(regionId: String): List<RegionStat> {
        val rng = Random(regionId.hashCode())
        return cities.shuffled(rng).take(8).mapIndexed { idx, city ->
            RegionStat("${regionId}_$idx", city, rng.nextInt(1, 60))
        }.sortedByDescending { it.count }
    }

    fun generateBlockedNumbers(count: Int = 20): List<BlockedNumber> {
        val reasons = listOf("Reported as spam", "Telemarketing", "Manually blocked", "Robocall detected", "Fraud alert")
        return (1..count).map { i ->
            val rng = Random(i * 23)
            val hasName = rng.nextInt(0, 100) < 30
            BlockedNumber(
                id = "blocked_$i",
                phoneNumber = randomIndianNumber(i * 19),
                name = if (hasName) "${firstNames.random(rng)} ${lastNames.random(rng)}" else null,
                reason = reasons.random(rng)
            )
        }
    }

    fun generateSearchHistory(count: Int = 10): List<String> =
        (1..count).map { randomIndianNumber(it * 41) }
}
