package com.nativedevps.support.utility.adapter

import android.R
import android.content.Context
import android.widget.ArrayAdapter

fun Context.simpleArrayAdapter(stringList: List<String>): ArrayAdapter<String> {
    return ArrayAdapter(
        this,
        R.layout.simple_spinner_item, stringList
    ).also {
        it.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
    }
}