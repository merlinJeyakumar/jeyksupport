package com.nativedevps.support.base_class.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.viewbinding.ViewBinding
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

        preInit()
        setContentView(binding.root)
        onCreate()
    }

    open fun preInit() {

    }

    override fun show() {
        return super.show().also {
            onShow(this)
        }
    }

    abstract fun onCreate()

    protected open fun onShow(alertDialog: AlertDialog?) {}
}