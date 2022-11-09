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
            messageAppCompatTextView.setText(text)
        }

    var progress: Int = -1
        set(value) = with(binding) {
            progress = value
        }

    companion object {
        fun build(context: Context): LoaderDialog {
            return LoaderDialog(context).also {
                it.show()
            }
        }
    }
}