package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.WindowManager
import com.nativedevps.support.base_class.dialog.FramedAlertDialog
import nativedevps.support.R
import nativedevps.support.databinding.DialogInputBinding

class InputDialog(context: Context) : FramedAlertDialog<DialogInputBinding>(
    context = context,
    bindingFactory = DialogInputBinding::inflate,
    theme = R.style.TransparentDialogStyle
) {

    override fun onCreate(): Unit = with(binding) {
        super.onCreate()

        initListener()
    }

    private fun initListener() = with(childBinding) {
        //noop
    }

    fun setSpannableMessage(charSequence:CharSequence)= with(childBinding){
        messageAppCompatTextView.text = charSequence
    }

    var message = ""
        set(text) = with(childBinding) {
            messageAppCompatTextView.text = text
        }

    var hint = ""
        set(text) = with(childBinding) {
            inputTextInputLayout.hint = text
        }

    var inputDefault = ""
        set(text) = with(childBinding) {
            inputTextInputLayout.editText?.setText(text)
        }

    var error: String? = ""
        set(text) = with(childBinding) {
            if (text != null) {
                inputTextInputLayout.isErrorEnabled = true
                inputTextInputLayout.error = text
            } else {
                inputTextInputLayout.isErrorEnabled = false
                inputTextInputLayout.error = null
            }
        }

    val textInputLayout get() = childBinding.inputTextInputLayout

    val text get() = childBinding.inputTextInputLayout.editText?.text.toString()

    companion object {
        fun build(context: Context): InputDialog {
            return InputDialog(context).also {
                it.show()
            }
        }
    }
}