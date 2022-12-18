package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.ViewGroup.LayoutParams
import android.view.WindowManager
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import com.nativedevps.support.base_class.Inflate
import com.nativedevps.support.base_class.dialog.FramedAlertDialog
import nativedevps.support.R
import nativedevps.support.databinding.DialogInformationBinding
import nativedevps.support.databinding.ItemChecklistBinding
import org.jetbrains.anko.layoutInflater

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

    fun isChecked(id: String) = with(childBinding) {
        return@with getCheckBox(id).isChecked
    }

    fun getCheckBox(id: String) = with(childBinding) {
        return@with ItemChecklistBinding.bind(meow.children.first { it.tag == id }).materialCheckBox
    }

    fun setCheckBox(
        id: String,
        message: String,
        onSelection: ((Boolean) -> Unit)? = null,
    ): ItemChecklistBinding =
        with(childBinding) {
            return@with ItemChecklistBinding.inflate(context.layoutInflater).apply {
                materialCheckBox.setTag(id)
                materialCheckBox.text = message
                materialCheckBox.setOnClickListener {
                    onSelection?.invoke(materialCheckBox.isChecked)
                }
            }.also {
                childBinding.meow.addView(it.root,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
            }
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