package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.WindowManager
import com.nativedevps.support.base_class.dialog.FramedAlertDialog
import nativedevps.support.R
import nativedevps.support.databinding.DialogInformationBinding

class InformationDialog(context: Context) : FramedAlertDialog<DialogInformationBinding>(
    context = context,
    bindingFactory = DialogInformationBinding::inflate,
    theme = R.style.TransparentDialogStyle
) {

    override fun preInit() {
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }

    override fun onCreate(): Unit = with(binding) {
        super.onCreate()

        initListener()
        initPreview()
    }

    private fun initListener() = with(childBinding) {
        //noop
    }

    private fun initPreview() {
        //noop
    }

    var message = ""
        set(text) = with(childBinding) {
            messageAppCompatTextView.setText(text)
        }

    companion object {
        fun build(context: Context): InformationDialog {
            return InformationDialog(context).also {
                it.show()
            }
        }
    }
}