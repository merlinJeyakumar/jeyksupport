package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import com.nativedevps.support.base_class.dialog.BaseAlertDialog
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
    }

    private fun initListener() {
        //noop
    }

    var message: String = ""
        set(text) = with(binding) {
            message = text
        }

    var progress: Int = -1
        set(value) = with(binding) {
            progress = value
        }

    companion object {
        private var owner: Int = 0
        private var loaderDialog: LoaderDialog? = null

        fun build(context: Context): LoaderDialog {
            if (context.hashCode() != owner) {
                loaderDialog = LoaderDialog(context).also {
                    it.show()
                }
                owner = context.hashCode()
            } else {
                loaderDialog?.show()
            }
            return loaderDialog ?: error("cannot be null")
        }
    }
}