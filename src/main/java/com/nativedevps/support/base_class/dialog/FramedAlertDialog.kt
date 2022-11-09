package com.nativedevps.support.base_class.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.utility.view.ViewUtils.setBackgroundTint
import com.nativedevps.support.utility.view.ViewUtils.visibility
import nativedevps.support.R
import nativedevps.support.databinding.DialogFramedBinding
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.textColor

abstract class FramedAlertDialog<B : ViewBinding>(
    context: Context,
    bindingFactory: (LayoutInflater) -> B,
    theme: Int = R.style.FullScreenDialogStyle,
) : BaseAlertDialog<DialogFramedBinding>(
    context = context,
    bindingFactory = DialogFramedBinding::inflate,
    theme = theme
) {

    private var _binding: ViewBinding = bindingFactory.invoke(context.layoutInflater)
    protected val childBinding: B by lazy { _binding as B }
    private var actionCallback: ((Boolean) -> Unit)? = null

    override fun onCreate() {
        binding.frameLayout.addView(childBinding.root, containerLayoutParams())

        initListener()
        initPreview()
    }

    private fun initPreview() {
        hasButton = true
        hasNegativeButton = true
        hasPositiveButton = true
        negativeButtonText = "Cancel"
        positiveButtonText = "Ok"
    }

    private fun initListener() {
        binding.closeAppCompatImageView.setOnClickListener {
            dismiss()
        }
        binding.okButton.setOnClickListener {
            actionButton(true)
        }
        binding.cancelButton.setOnClickListener {
            actionButton(false)
        }
    }

    var headerTitle: String = ""
        set(text) = with(binding) {
            frameTitleAppCompatTextView.setText(text)
        }

    var headerBackgroundColor: Int = 0
        set(value) = with(binding) {
            headerParentLayout.backgroundColor = value
            okButton.setBackgroundTint(value)
        }

    var headerTextColor: Int = 0
        set(value) = with(binding) {
            frameTitleAppCompatTextView.textColor = value
            okButton.setTextColor(value)
        }

    var hasButton: Boolean = true
        set(value) = with(binding) {
            buttons.visibility(value)
        }

    var hasPositiveButton: Boolean = true
        set(value) = with(binding) {
            okButton.visibility(value)
        }

    var hasNegativeButton: Boolean = true
        set(value) = with(binding) {
            cancelButton.visibility(value)
        }

    var negativeButtonText: String = "Cancel"
        set(value) = with(binding) {
            cancelButton.setText(value)
        }

    var positiveButtonText: String = "Ok"
        set(value) = with(binding) {
            okButton.setText(value)
        }

    var hasDismissButton: Boolean = true
        set(value) = with(binding) {
            closeAppCompatImageView.visibility(value)
        }

    override fun setCancelable(flag: Boolean) {
        super.setCancelable(flag)
        hasDismissButton = flag
    }

    open fun onActionButton(actionCallback: (Boolean) -> Unit) {
        this.actionCallback = actionCallback
    }

    private fun actionButton(isOkButton: Boolean) {
        actionCallback?.invoke(isOkButton)
    }

    open fun containerLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}