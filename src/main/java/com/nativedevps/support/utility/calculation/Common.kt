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