package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.WindowManager
import com.nativedevps.support.base_class.dialog.BaseAlertDialog
import nativedevps.support.databinding.DialogInputBinding

class InputDialog(context: Context) : BaseAlertDialog<DialogInputBinding>(
    context = context,
    bindingFactory = DialogInputBinding::inflate,
    theme = 0
) {

    override fun preinit() {
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }

    override fun onCreate(binding: DialogInputBinding)= with(binding) {

    }
}