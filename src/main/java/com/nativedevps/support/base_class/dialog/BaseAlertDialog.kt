package com.nativedevps.support.base_class.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.utility.debugging.Log
import nativedevps.support.R
import org.jetbrains.anko.layoutInflater

abstract class BaseAlertDialog<B : ViewBinding>(
    context: Context,
    bindingFactory: (LayoutInflater) -> B,
    theme: Int = R.style.FullScreenDialogStyle,
) : AlertDialog(context, theme) {

    private var _binding: ViewBinding = bindingFactory.invoke(context.layoutInflater)
    protected val binding: B by lazy { _binding as B }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preInit()
        onCreate()
    }

    open fun preInit() {

    }

    override fun show() {
        if (!allowDuplicate() && isPrompting) {
            Log.e("BaseAlertDialog", "already displaying a alertdialog in duplicate allow mode")
            return
        }
        return super.show().also {
            isPrompting = true
            onShow(this)
        }
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        isPrompting = false
        super.setOnDismissListener(listener)
    }

    abstract fun onCreate()

    protected open fun onShow(alertDialog: AlertDialog?) {}

    open fun allowDuplicate(allow: Boolean = true): Boolean {
        return allow
    }

    companion object {
        var isPrompting = false
    }
}