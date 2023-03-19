package com.nativedevps.support.utility.date_time_utility

import android.text.format.DateUtils
import java.util.*
import java.util.concurrent.TimeUnit

object MillisecondUtility {
    val now: Long get() = System.currentTimeMillis()

    fun dayStartMillis(millis: Long): GregorianCalendar {
        val gregorianMillis = GregorianCalendar().apply {
            timeInMillis = millis
            //add(GregorianCalendar.DAY_OF_MONTH,-1)
        }

        gregorianMillis.set(GregorianCalendar.HOUR, 0)
        gregorianMillis.set(GregorianCalendar.MINUTE, 0)
        return gregorianMillis
    }

    fun dayEndMillis(millis: Long): Long {
        val gregorianMillis = GregorianCalendar().apply {
            timeInMillis = millis
        }

        gregorianMillis.set(GregorianCalendar.HOUR, 23)
        gregorianMillis.set(GregorianCalendar.MINUTE, 59)
        gregorianMillis.set(GregorianCalendar.SECOND,59)

        return gregorianMillis.timeInMillis
    }

    fun yesterday(
        millis: Long,
        adjustment: Int = -1,
        resetTime: Boolean = true,
    ): Long {
        return adjustTime(millis, adjustment, resetTime)
    }

    fun tomorrow(
        millis: Long,
        adjustment: Int = 1,
        resetTime: Boolean = true,
    ): Long {
        return adjustTime(millis, adjustment, resetTime)
    }

    fun adjustTime(
        millis: Long,
        adjustment: Int,
        resetTime: Boolean = true,
    ): Long {
        val gregorianMillis = if (resetTime) {
            dayStartMillis(millis)
        } else {
            GregorianCalendar().apply {
                timeInMillis = millis
            }
        }
        gregorianMillis.add(GregorianCalendar.DAY_OF_YEAR, adjustment)
        return gregorianMillis.timeInMillis
    }

    fun Long.isToday(): Boolean {
        return DateUtils.isToday(this)
    }
}