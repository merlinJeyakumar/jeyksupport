package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.WindowManager
import com.nativedevps.support.base_class.dialog.FramedAlertDialog
import com.nativedevps.support.utility.view.ViewUtils.visible
import nativedevps.support.R
import nativedevps.support.databinding.DialogInformationBinding

class ConfirmationDialog(context: Context) : FramedAlertDialog<DialogInformationBinding>(
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

    fun setCheckBox(
        label: String,
        checked: Boolean = false,
        callback: ((boolean: Boolean) -> Unit)? = null
    ) = with(childBinding)
    {
        todoCheckBox.apply {
            visible()
            setText(label)
            todoCheckBox.isChecked = checked
            setOnClickListener {
                callback?.invoke(todoCheckBox.isChecked)
            }
        }
    }

    fun isChecked(): Boolean = with(childBinding) {
        return todoCheckBox.isChecked
    }

    var message = ""
        set(text) = with(childBinding) {
            messageAppCompatTextView.setText(text)
        }

    companion object {
        fun build(context: Context): ConfirmationDialog {
            return ConfirmationDialog(context).also {
                it.show()
            }
        }
    }
}