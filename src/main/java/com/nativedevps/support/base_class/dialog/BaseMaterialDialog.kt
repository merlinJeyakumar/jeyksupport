package com.nativedevps.support.base_class.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nativedevps.support.inline.orElse
import org.jetbrains.anko.layoutInflater

abstract class BaseMaterialDialog<B : ViewBinding>(
    context: Context,
    private val bindingFactory: (LayoutInflater) -> B,
    private val theme: Int = 0
) : MaterialAlertDialogBuilder(context, theme) {

    protected var alertDialog: AlertDialog? = null
    private var _binding: ViewBinding = bindingFactory.invoke(context.layoutInflater)
    protected val binding: B by lazy { _binding as B }

    override fun create(): AlertDialog {
        preinit()
        setView(binding.root)
        return super.create()
    }

    open fun preinit() {

    }

    override fun setView(view: View?): MaterialAlertDialogBuilder {
        super.setView(binding.root).let {
            return it.also {
                onCreate(binding)
            }
        }
    }

    override fun show(): AlertDialog {
        return super.show().also {
            this.alertDialog = it
            onShow(alertDialog)
        }
    }

    fun dismiss() {
        this.alertDialog?.dismiss()
    }

    open fun isShowing(): Boolean {
        return alertDialog?.isShowing.orElse { false }
    }

    abstract fun onCreate(binding: B)

    protected open fun onShow(alertDialog: AlertDialog?) {}
}