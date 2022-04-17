package com.nativedevps.support.utility.date_time_utility

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtility {
    fun getTimeDifference(
        fromMilliSecond: String,
        toMilliSecond: String,
        mUnitType: Int,
    ): Int {
        //0 = Seconds
        //1 = Minutes
        //2 = Hours
        //3 = Days
        val localCreatedDate = SimpleDateFormat("dd-M-yyyy HH:mm:ss aa", Locale.getDefault())
            .format(Date(toMilliSecond.toLong()))
        val localExpireDate = SimpleDateFormat("dd-M-yyyy HH:mm:ss aa", Locale.getDefault())
            .format(Date(fromMilliSecond.toLong()))
        //Log.i(TAG, "get_time_difference: localCreatedDate " + localCreatedDate);
        //Log.i(TAG, "get_time_difference: localExpireDate " + localExpireDate);
        val dateFormat = SimpleDateFormat("dd-M-yyyy HH:mm:ss aa", Locale.getDefault())
        var Created_convertedDate: Date? = null
        var Expire_CovertedDate: Date? = null
        var todayWithZeroTime: Date? = null
        try {
            Created_convertedDate = dateFormat.parse(localCreatedDate)
            Expire_CovertedDate = dateFormat.parse(localExpireDate)
            val today = Date()
            todayWithZeroTime = dateFormat.parse(dateFormat.format(today))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val timeDiff = Expire_CovertedDate!!.time - Created_convertedDate!!.time
        return when (mUnitType) {
            0 -> {
                TimeUnit.SECONDS.convert(
                    timeDiff,
                    TimeUnit.MILLISECONDS
                ).toInt()
            }
            1 -> {
                TimeUnit.MINUTES.convert(
                    timeDiff,
                    TimeUnit.MILLISECONDS
                ).toInt()
            }
            2 -> {
                TimeUnit.HOURS.convert(
                    timeDiff,
                    TimeUnit.MILLISECONDS
                ).toInt()
            }
            3 -> {
                TimeUnit.DAYS.convert(
                    timeDiff,
                    TimeUnit.MILLISECONDS
                ).toInt()
            }
            else -> {
                TimeUnit.MINUTES.convert(
                    timeDiff,
                    TimeUnit.MILLISECONDS
                ).toInt()
            }
        }
    }

    fun getTimeDifference(
        fromMilliSecond: Long,
        toMilliSecond: Long,
        mUnitType: Int,
    ): Int {
        return getTimeDifference(fromMilliSecond.toString(), toMilliSecond.toString(), mUnitType)
    }
}