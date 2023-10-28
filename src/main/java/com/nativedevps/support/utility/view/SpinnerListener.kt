package com.nativedevps.support.utility.view

import android.view.View
import android.widget.AdapterView

abstract class SpinnerListener : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}