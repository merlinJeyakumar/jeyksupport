package com.nativedevps.support.utility.view

import android.app.Activity
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.nativedevps.support.custom_views.ArrayDrawableListViewAdapter
import nativedevps.support.R
import nativedevps.support.databinding.DialogInformationBinding
import nativedevps.support.databinding.DialogInputBinding
import nativedevps.support.databinding.DialogListBinding


object DialogBox {
    const val InputType_WebEmail = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
    const val InputType_WebPassword =
        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    const val InputType_WebText = InputType.TYPE_CLASS_TEXT


    fun Activity.confirmationDialog(
        title: String = "Alert",
        message: String = "Are you sure?",
        isCancellable: Boolean = true,
        negativeText: String = getString(R.string.cancel),
        positiveText: String = getString(R.string.ok),
        callback: (success: Boolean) -> Unit,
    ): AlertDialog {
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
        materialAlertDialogBuilder.setTitle(title)
        materialAlertDialogBuilder.setCancelable(isCancellable)
        message.let {
            materialAlertDialogBuilder.setMessage(it)
        }
        materialAlertDialogBuilder.setNegativeButton(negativeText) { dialog, which ->
            materialAlertDialogBuilder.create().dismiss()
            callback(false)
        }
        materialAlertDialogBuilder.setPositiveButton(positiveText) { dialog, which ->
            materialAlertDialogBuilder.create().dismiss()
            callback(true)
        }
        return materialAlertDialogBuilder.show()
    }

    fun <T> Activity.listDialog(
        title: String? = "Alert",
        isCancellable: Boolean = true,
        stringList: List<T>,
        negativeText: String? = getString(R.string.cancel),
        callback: (success: Boolean, selection: Pair<Int, T>?) -> Unit,
    ): AlertDialog {
        var alertDialog: AlertDialog? = null
        val binding = DialogListBinding.inflate(layoutInflater)
        binding.itemsListView.adapter = ArrayDrawableListViewAdapter(this, stringList)
        binding.itemsListView.setOnItemClickListener { adapterView, view, position, id ->
            alertDialog?.dismiss()
            callback(true, Pair(position, stringList[position]))
        }
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
        materialAlertDialogBuilder.setView(binding.root)
        materialAlertDialogBuilder.setTitle(title)
        materialAlertDialogBuilder.setCancelable(isCancellable)

        if (negativeText != null) {
            materialAlertDialogBuilder.setNegativeButton(negativeText) { dialog, which ->
                alertDialog?.dismiss()
                callback(false, null)
            }
        }

        return materialAlertDialogBuilder.show().apply {
            alertDialog = this
            alertDialog?.setOnCancelListener {
                callback(false, null)
            }
        }
    }

    fun Activity.informationDialog(
        title: String? = "Alert",
        message: String,
        isCancellable: Boolean = true,
        negativeText: String? = getString(R.string.close),
        positiveText: String? = getString(R.string.ok),
        callback: ((positive: Boolean) -> Unit?)? = null,
    ): AlertDialog {
        var alertDialog: AlertDialog?
        val binding = DialogInformationBinding.inflate(layoutInflater).apply {
            messageAppCompatTextView.setText(message)
            messageAppCompatTextView.setMovementMethod(ScrollingMovementMethod())
        }

        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
        materialAlertDialogBuilder.setView(binding.root)
        materialAlertDialogBuilder.setTitle(title)
        materialAlertDialogBuilder.setCancelable(isCancellable)

        if (negativeText.isNullOrEmpty()) {
            materialAlertDialogBuilder.setNegativeButton(negativeText) { dialog, which ->
                materialAlertDialogBuilder.create().dismiss()
                callback?.invoke(false)
            }
        }
        if (positiveText.isNullOrEmpty()) {
            throw NullPointerException("cannot be empty")
        }

        materialAlertDialogBuilder.setPositiveButton(positiveText) { dialog, which ->
            materialAlertDialogBuilder.create().dismiss()
            callback?.invoke(true)
        }

        return materialAlertDialogBuilder.show().apply {
            alertDialog = this
            alertDialog?.setOnCancelListener {
                callback?.invoke(false)
            }
        }
    }

    /**
     * suggestion: pass message without title for better ui
     **/
    fun Activity.inputDialog(
        title: String = getString(R.string.app_name),
        message: String = "",
        default: String = "",
        hint: String = "required",
        inputType: Int = InputType_WebEmail,
        isCancellable: Boolean = true,
        negativeText: String? = getString(R.string.cancel),
        positiveText: String? = getString(R.string.ok),
        dismissOnPositive: Boolean = true,
        setup: ((textInputLayout: TextInputLayout?) -> Unit)? = null,
        callback: (
            alertDialog: AlertDialog?,
            positive: Boolean,
            textInputLayout: TextInputLayout?,
        ) -> Unit,
    ): AlertDialog {
        var alertDialog: AlertDialog? = null
        val binding = DialogInputBinding.inflate(layoutInflater).apply {
            messageAppCompatTextView.setText(message)
            messageAppCompatTextView.setMovementMethod(ScrollingMovementMethod())

            inputTextInputLayout.isHintEnabled = true
            inputTextInputLayout.hint = hint
            inputTextInputLayout.isPasswordVisibilityToggleEnabled =
                inputType == InputType_WebPassword

            inputTextInputLayout.editText?.apply {
                setInputType(inputType)
                setText(default)
            }
            setup?.invoke(inputTextInputLayout)
        }

        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
        materialAlertDialogBuilder.setView(binding.root)
        materialAlertDialogBuilder.setTitle(title)
        materialAlertDialogBuilder.setCancelable(isCancellable)

        if (!negativeText.isNullOrEmpty()) {
            materialAlertDialogBuilder.setNegativeButton(negativeText) { dialog, which ->
                materialAlertDialogBuilder.create().dismiss()
                callback(null, false, binding.inputTextInputLayout)
            }
        }
        if (positiveText.isNullOrEmpty()) {
            throw NullPointerException("cannot be empty")
        }

        materialAlertDialogBuilder.setPositiveButton(positiveText, null)

        return materialAlertDialogBuilder.show().apply {
            alertDialog = this
            this.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
            );
            alertDialog?.setOnCancelListener {
                callback(alertDialog, false, null)
            }
            alertDialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.apply {
                setOnClickListener {
                    if (dismissOnPositive) {
                        alertDialog?.dismiss()
                    }
                    callback(alertDialog, true, binding.inputTextInputLayout)
                }
            }
        }
    }
}