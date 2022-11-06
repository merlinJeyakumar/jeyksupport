package com.nativedevps.support.custom_views

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ScrollingTextView : AppCompatTextView {

    constructor(context: Context) : super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr){
        init()
    }

    private fun init() {
        movementMethod = ScrollingMovementMethod()
    }
}