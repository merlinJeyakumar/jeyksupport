package com.nativedevps.support.utility.fragment

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import nativedevps.support.R
import org.jetbrains.anko.toast

fun Fragment.setResult(key: String, value: Any) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, value)
}

fun <T> Fragment.onResult(key: String): MutableLiveData<T>? {
    return findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
}

fun Activity.goto(hostFragmentId: Int = R.id.nav_host_fragment, id: Int, bundle: Bundle) {
    findNavController(hostFragmentId).navigate(id, bundle)
}

fun Activity.goto(hostFragmentId: Int = R.id.nav_host_fragment, id: Int) {
    findNavController(hostFragmentId).navigate(id)
}

fun Fragment.goto(id: Int) {
    activity?.let {
        findNavController().navigate(id)
    }
}

fun Fragment.goto(id: Int, bundle: Bundle) {
    activity?.let {
        findNavController().navigate(id, bundle)
    }
}

fun Fragment.goto(directions: NavDirections) {
    activity?.let {
        findNavController().navigate(directions)
    }
}

fun Fragment.navigateUp() {
    activity?.let {
        findNavController().navigateUp()
    }
}

fun Fragment.popBackStack() {
    activity?.let {
        findNavController().popBackStack()
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.softInputModeAdjustResize() {
    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
}

fun Fragment.softInputModeNothing() {
    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
}

fun Fragment.copyToClipboard(label: CharSequence, text: CharSequence, callback: (Unit) -> Unit) {
    activity?.let {
        (it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?)?.apply {
            setPrimaryClip(ClipData.newPlainText(label, text))
            callback.invoke(Unit)
        }
    }
}

fun Fragment.toast(message: String) {
    activity?.toast(message)
}

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit,
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}