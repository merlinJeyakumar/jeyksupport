package com.nativedevps.support.utility.calculation

import java.text.DecimalFormat
import java.util.*

fun getRandomNumber(maxNumber: Int, isFromZero: Boolean): Int {
    return if (isFromZero) {
        Random().nextInt(maxNumber)
    } else {
        Random().nextInt(maxNumber) + 1
    }
}

fun Double.decimalFormat(floatPointLevel: Int = 2): String {
    return DecimalFormat("##.${"$".repeat(floatPointLevel)}").format(this)
}

/**
 * Calculate progress percentage.
 *
 * @param wholeNumber The total value (100%).
 * @param progressNumber The current value.
 * @return The progress as a percentage (0-100).
 */
fun <N : Number> calculateProgress(wholeNumber: N, progressNumber: N): Int {
    val whole = wholeNumber.toDouble()
    val progress = progressNumber.toDouble()
    return if (whole > 0) ((progress / whole) * 100).toInt() else 0
}