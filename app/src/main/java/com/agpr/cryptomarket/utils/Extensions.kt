package com.agpr.cryptomarket.utils

import java.text.DecimalFormat

fun Double?.toCurrency(): String {
    return DecimalFormat("###,###,##0.00").format(this ?: 0.0)
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
