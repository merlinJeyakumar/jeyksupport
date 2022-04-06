package com.nativedevps.support.utility.device.keyboard

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by Kumar on 08/09/18.
 */

fun Activity.closeKeyboard(view: View?) {
    if (view != null) {
        getInputMethodService(this).hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.closeKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        getInputMethodService(this).hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.SHOW_FORCED
        )
    }
}

fun Context.openKeyboard(view: View) {
    Handler().post {
        getInputMethodService(this).showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }
}


fun Context.showSoftKeyboard(editText: EditText) {
    try {
        editText.requestFocus()
        editText.postDelayed(
                {
                    getInputMethodService(this).showSoftInput(editText, 0)
                }, 200
        )
    } catch (npe: NullPointerException) {
        npe.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun getInputMethodService(context: Context): InputMethodManager {
    return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}