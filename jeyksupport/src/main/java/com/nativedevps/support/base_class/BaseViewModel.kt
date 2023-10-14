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
    val liveDataErrorAction = MutableLiveData<String>()

    abstract fun onCreate()

    open fun showProgressDialog(
        message: String = "loading..",
        progress: Int = -1,
        cancelable: Boolean = false,
    ) {
        context.runOnUiThread {
            Log.e("BaseViewModel", "ShowProgressDialog")
            liveDataProgressBar.value = LoaderProperties(
                true,
                message,
                progress,
                cancelable
            )
        }
    }

    open fun hideProgressDialog() {
        context.runOnUiThread {
            Log.e("BaseViewModel", "hideProgressDialog")
            liveDataProgressBar.value = LoaderProperties(
                false
            )
        }
    }

    open fun toast(string: String) {
        context.toast(string)
    }

    open fun onProgressDialogCancelled() {
    }

    open fun handleError(exception: java.lang.Exception) {
        handleError(exception.message?:exception.localizedMessage)
    }

    open fun handleError(message: String) {
        context.runOnUiThread {
            liveDataErrorAction.value = message
        }
    }
}