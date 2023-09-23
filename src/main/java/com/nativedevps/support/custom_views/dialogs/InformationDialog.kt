package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.WindowManager
import com.nativedevps.support.base_class.dialog.FramedAlertDialog
import com.nativedevps.support.custom_views.ScrollingTextView
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
        hasNegativeButton = false
    }

    var message = ""
        set(text) = with(childBinding) {
            messageAppCompatTextView.setText(text)
        }

    fun spannableMessage(spannableStringBuilder: SpannableStringBuilder)= with(childBinding){
        messageAppCompatTextView.text = spannableStringBuilder
    }

    fun textView(): ScrollingTextView = with(childBinding){
        return messageAppCompatTextView
    }

    companion object {
        fun build(context: Context): InformationDialog {
            return InformationDialog(context).also {
                it.show()
            }
        }
    }
}