package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import com.nativedevps.support.base_class.dialog.BaseAlertDialog
import com.nativedevps.support.model.LoaderProperties
import nativedevps.support.databinding.DialogProgressBarBinding

class LoaderDialog(
    context: Context,
) : BaseAlertDialog<DialogProgressBarBinding>(
    context = context,
    bindingFactory = DialogProgressBarBinding::inflate,
    0
) {

    override fun onCreate() {

        initListener()
        initPreview()
    }

    private fun initPreview() = with(binding) {
        progress = 0
    }

    private fun initListener() {
        //noop
    }

    var message: String = ""
        set(text) = with(binding) {
            messageAppCompatTextView.setText(text)
        }

    var progress: Int = 0
        set(value) = with(binding) {
            progress = value
        }

    fun setLoaderProperties(loaderProperties: LoaderProperties) {
        loaderProperties.progress = loaderProperties.progress
        setCancelable(loaderProperties.cancellable)
        message = loaderProperties.message
        allowDuplicate(false)
    }

    companion object {
        fun build(context: Context): LoaderDialog {
            return LoaderDialog(context).also {
                it.show()
            }
        }
    }
}