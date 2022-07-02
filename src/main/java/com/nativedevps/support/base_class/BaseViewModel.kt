package com.nativedevps.support.base_class

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nativedevps.support.model.LoaderProperties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

abstract class BaseViewModel constructor(application: Application) :
    AndroidViewModel(application) {
    protected val context: Context get() = getApplication<Application>()
    val liveDataProgressBar = MutableLiveData<LoaderProperties>()

    abstract fun onCreate()

    open fun showProgressDialog(
        message: String = "loading..",
        progress: Int = -1,
        cancelable: Boolean = false
    ) {
        Log.e("JK", "showProgressDialog")
        context.runOnUiThread {
            liveDataProgressBar.value = LoaderProperties(
                true,
                message,
                progress,
                cancelable
            )
        }
    }

    open fun hideProgressDialog() {
        Log.e("JK", "hideProgressDialog")
        context.runOnUiThread {
            liveDataProgressBar.value = LoaderProperties(
                false
            )
        }
    }

    open fun toast(string: String) {
        context.toast(string)
    }

    @Deprecated("use com.nativedevps.support.utility.threading functions")
    fun runOnNewThread(callback: suspend CoroutineScope.() -> Unit): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            callback()
        }
    }

    @Deprecated("use com.nativedevps.support.utility.threading functions")
    fun runOnUiThread(callback: () -> Unit) {
        context.runOnUiThread {
            callback()
        }
    }

    open fun onProgressDialogCancelled() {
    }
}