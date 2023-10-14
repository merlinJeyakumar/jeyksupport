package com.nativedevps.support.utility.date_time_utility

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val DateTimePattern = "HH:mm:ss - dd/MM/yyyy"
const val fancyDatePattern = "dd-MM-yyyy"
const val fancyDateTimePattern = "dd-MM-yyyy HH:mm"

fun isValidDate(
    inDate: String,
    separator: String,
    pattern: String = "dd${separator}MM${separator}yyyy",
): Boolean {
    val dateFormat =
        SimpleDateFormat(pattern, Locale.getDefault())
    dateFormat.isLenient = false
    try {
        dateFormat.parse(inDate.trim { it <= ' ' })
    } catch (pe: ParseException) {
        return false
    }
    return true
}

fun parseStringDateToCalendar(
    inputString: String?,
    formatPattern: String?,
): Calendar? {
    // EEE MMM dd HH:mm:ss z yyyy
    // Mon Mar 14 16:02:37 GMT 2011
    val calendar = Calendar.getInstance()
    val simpleDateFormat = SimpleDateFormat(formatPattern, Locale.getDefault())
    try {
        calendar.time = simpleDateFormat.parse(inputString) // all done
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return calendar
}

fun parseDateFromMilliseconds(
    milliSecond: Long,
    formatPattern: String = DateTimePattern,
): String? {
    //dd-M-yyyy hh:mm:ss
    return SimpleDateFormat(formatPattern, Locale.getDefault())
        .format(Date(milliSecond))
}

fun parseMillisFromString(formatPattern: String?): Long {
    //String date_ = date;
    val sdf = SimpleDateFormat("dd-M-yyyy HH:mm:ss", Locale.getDefault())
    return try {
        val mDate = sdf.parse(formatPattern)
        val timeInMilliseconds = mDate.time
        println("Date in milli :: $timeInMilliseconds")
        timeInMilliseconds
    } catch (e: ParseException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
        0
    }
}

fun getCalendarDifference(
    mFirstCalendar: Calendar,
    mSecondCalendar: Calendar,
    mTimeUnit: Int,
): Long {
    //0 = Seconds
    //1 = Minutes
    //2 = Hours
    //3 = Days
    when (mTimeUnit) {
        0 -> {
            return TimeUnit.MILLISECONDS.toSeconds(mFirstCalendar.timeInMillis - mSecondCalendar.timeInMillis)
        }
        1 -> {
            return TimeUnit.MILLISECONDS.toMinutes(mFirstCalendar.timeInMillis - mSecondCalendar.timeInMillis)
        }
        2 -> {
            return TimeUnit.MILLISECONDS.toHours(mFirstCalendar.timeInMillis - mSecondCalendar.timeInMillis)
        }
        3 -> {
            return TimeUnit.MILLISECONDS.toDays(mFirstCalendar.timeInMillis - mSecondCalendar.timeInMillis)
        }
    }
    return 0
}

fun getParsedTodayDateTime(pattern: String = DateTimePattern): String? {
    return parseDateFromMilliseconds(System.currentTimeMillis(), pattern)
}

fun getTodayDateTime(separator: String): String? {
    val mPattern = "dd${separator}MM${separator}yyyy HH:mm:ss" // dd-mm-yyyy
    return parseDateFromMilliseconds(System.currentTimeMillis(), mPattern)
}

fun getDateTimeMillis(separator: String, millis: Long): String? {
    val mPattern = "dd${separator}MM${separator}yyyy HH:mm:ss" // dd-mm-yyyy
    return parseDateFromMilliseconds(millis, mPattern)
}

fun getCalendarMillis(millis: Long): Calendar {
    val mCalendar = Calendar.getInstance()
    mCalendar.timeInMillis = millis
    return mCalendar
}

fun getDateMillis(millis: Long): Date? {
    return getCalendarMillis(millis).time
}

fun getGregorianMillis(millis: Long): GregorianCalendar {
    val gregorianCalendar = GregorianCalendar()
    gregorianCalendar.timeInMillis = millis
    return gregorianCalendar
}

fun addYearToMillis(timeInMillis: Long, years: Int): Long {
    val gregorian = getGregorianMillis(timeInMillis)
    gregorian.add(Calendar.YEAR, years)
    return gregorian.timeInMillis
}

fun addMonthToMillis(timeInMillis: Long, months: Int): Long {
    val gregorian = getGregorianMillis(timeInMillis)
    gregorian.add(Calendar.MONTH, months)
    return gregorian.timeInMillis
}

fun addDayToMillis(timeInMillis: Long, days: Int): Long {
    val gregorian = getGregorianMillis(timeInMillis)
    gregorian.add(Calendar.DAY_OF_MONTH, days)
    return gregorian.timeInMillis
}

fun getYearMillis(timeInMillis: Long): Int {
    //Log.i(TAG, "getYearMillis: year " + getCalendarMillis(timeInMillis).get(Calendar.YEAR));
    return getCalendarMillis(timeInMillis)[Calendar.YEAR]
}

fun getAge(millis: Long?): Int {
    val now = Calendar.getInstance()
    val dob = Calendar.getInstance()
    dob.timeInMillis = millis!!
    require(!dob.after(now)) { "Can't be born in the future" }
    val year1 = now[Calendar.YEAR]
    val year2 = dob[Calendar.YEAR]
    var age = year1 - year2
    val month1 = now[Calendar.MONTH]
    val month2 = dob[Calendar.MONTH]
    if (month2 > month1) {
        age--
    } else if (month1 == month2) {
        val day1 = now[Calendar.DAY_OF_MONTH]
        val day2 = dob[Calendar.DAY_OF_MONTH]
        if (day2 > day1) {
            age--
        }
    }
    return age
}

fun addDate(initialDate: Date?, daysToAdd: Int): Date? {
    val mGregorian =
        GregorianCalendar(TimeZone.getTimeZone("GMT"))
    mGregorian.time = initialDate
    mGregorian.add(Calendar.DATE, daysToAdd)
    return mGregorian.time
}

fun addDate(initialDate: Long, daysToAdd: Int): Date? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = initialDate
    return addDate(calendar.time, daysToAdd)
}

fun addDate(initialDate: String, daysToAdd: Int): Date? {
    return addDate(initialDate.toLong(), daysToAdd)
}

fun addDate(initialDate: Calendar, daysToAdd: Int): Date? {
    return addDate(initialDate.time, daysToAdd)
}

fun getStringDateFromMillis(
    milliSecond: Long,
    formatPattern: String?,
): String? {
    //dd-M-yyyy hh:mm:ss
    return SimpleDateFormat(formatPattern, Locale.getDefault())
        .format(Date(milliSecond))
}

fun formatDuration(millis: Long): String? {
    val seconds = millis / 1000 % 60
    val minutes = millis / (1000 * 60) % 60
    val hours = millis / (1000 * 60 * 60)
    val b = StringBuilder()
    /*b.append(if (hours == 0L) "00" else if (hours < 10) "0$hours" else hours.toString())
    b.append(":")
    b.append(if (minutes == 0L) "00" else if (minutes < 10) "0$minutes" else minutes.toString())*/
    b.append(if (seconds == 0L) "00" else if (seconds < 10) "0$seconds" else seconds.toString())
    b.append(" Sec")
    return b.toString()
}

fun getCalendarFromTime(TimeHHmm: String): Calendar {
    val localArray = TimeHHmm.split(":").toTypedArray()
    val calendar = Calendar.getInstance()
    calendar[Calendar.HOUR_OF_DAY] = localArray[0].toInt()
    calendar[Calendar.MINUTE] = localArray[1].toInt()
    calendar[Calendar.SECOND] = 0
    return calendar
}
