package com.phonecalltrue.app.utils

object PhoneNumberUtils {

    /** Strips everything but digits, then drops a leading country code of 91 for 12-digit numbers. */
    fun normalize(raw: String): String {
        val digits = raw.filter { it.isDigit() }
        return if (digits.length == 12 && digits.startsWith("91")) digits.substring(2) else digits
    }

    fun isValid(raw: String): Boolean {
        val normalized = normalize(raw)
        return normalized.length == 10 || (normalized.length in 7..15)
    }

    fun formatIndian(normalized: String): String {
        val digits = normalize(normalized)
        return if (digits.length == 10) {
            "+91 ${digits.substring(0, 5)} ${digits.substring(5)}"
        } else {
            digits
        }
    }

    fun displayFormat(raw: String): String = if (isValid(raw)) formatIndian(raw).ifBlank { raw } else raw
}
