package com.agpr.cryptomarket.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Double?.toCurrency(decimalPlaces: Int = 2): String {
    val pattern = buildString {
        append("#,##0") // Comma as thousands separator
        if (decimalPlaces > 0) {
            append(".")
            repeat(decimalPlaces) { append("0") }
        }
    }
    val decimalFormat = DecimalFormat(pattern)
    decimalFormat.decimalFormatSymbols =
        DecimalFormatSymbols(Locale.US) // Ensure commas are properly used
    return decimalFormat.format(this)
}

fun Double?.toMarketCap(): String {
    if (this != null) {
        val magnitude = when {
            this >= 1_000_000_000_000L -> "T"
            this >= 1_000_000_000L -> "B"
            this >= 1_000_000L -> "M"
            this >= 1_000L -> "T"
            else -> ""
        }

        val divisor = when {
            this >= 1_000_000_000_000L -> 1_000_000_000_000L
            this >= 1_000_000_000L -> 1_000_000_000L
            this >= 1_000_000L -> 1_000_000L
            this >= 1_000L -> 1_000L
            else -> 1L
        }

        val formattedAmount =
            (this / divisor).toBigDecimal().setScale(2, java.math.RoundingMode.HALF_UP).toString()

        return "$$formattedAmount $magnitude"
    } else return "$0.0"
}
