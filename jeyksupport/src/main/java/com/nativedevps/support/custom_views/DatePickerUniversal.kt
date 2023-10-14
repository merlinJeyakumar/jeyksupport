package com.nativedevps.support.custom_views

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.DatePicker
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author JeyK at 25-03-2022
 */
class DatePickerUniversal(
    private val mEditText: EditText,
    format: String?
) : OnDateSetListener {
    private var mCalendar: Calendar = Calendar.getInstance()
    private val mFormat: SimpleDateFormat = SimpleDateFormat(format, Locale.getDefault())

    fun showPicker(): DatePickerDialog {
        val day = mCalendar[Calendar.DAY_OF_MONTH]
        val month = mCalendar[Calendar.MONTH]
        val year = mCalendar[Calendar.YEAR]
        return DatePickerDialog(mEditText.context, this, year, month, day).apply {
            show()
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        mCalendar[Calendar.YEAR] = year
        mCalendar[Calendar.MONTH] = month
        mCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        mEditText.setText(mFormat.format(mCalendar.time))
    }

}