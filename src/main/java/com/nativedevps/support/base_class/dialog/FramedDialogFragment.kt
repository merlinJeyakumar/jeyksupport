package com.nativedevps.support.base_class.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.base_class.BaseDialogFragment
import com.nativedevps.support.base_class.BaseViewModel
import com.nativedevps.support.utility.view.ViewUtils.setBackgroundTint
import com.nativedevps.support.utility.view.ViewUtils.visibility
import nativedevps.support.R
import nativedevps.support.databinding.DialogFramedBinding
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.layoutInflater

abstract class FramedDialogFragment<B : ViewBinding, VM: BaseViewModel>(
    context: Context,
    bindingFactory: (LayoutInflater) -> B,
    viewModelClass: Class<VM>,
) : BaseDialogFragment<DialogFramedBinding, VM>(
    DialogFramedBinding::inflate,
    viewModelClass
) {

    private var _binding: ViewBinding = bindingFactory.invoke(context.layoutInflater)
    protected val childBinding: B by lazy { _binding as B }
    private var actionCallback: ((Boolean) -> Unit)? = null

    override fun onInit(savedInstanceState: Bundle?) {
        binding.frameLayout.addView(childBinding.root, containerLayoutParams())

        initListener()
        initToolbar()
        initPreview()
    }

    private fun initToolbar() = with(binding) {
        toolbar.setTitle(requireContext().resources.getString(R.string.app_name))
        toolbar.inflateMenu(createActionMenu())
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menuCloseAction) {
                dismiss()
            }
            return@setOnMenuItemClickListener true
        }
        prepareActionMenu(toolbar.menu)
    }

    private fun initPreview() {
        hasButton = true
        hasNegativeButton = true
        hasPositiveButton = true
        negativeButtonText = "Cancel"
        positiveButtonText = "Ok"
    }

    private fun initListener() = with(binding) {
        okButton.setOnClickListener {
            actionButton(true)
        }
        cancelButton.setOnClickListener {
            actionButton(false)
        }
    }

    var headerTitle: String = ""
        set(text) = with(binding) {
            toolbar.setTitle(text)
        }

    var headerBackgroundColor: Int = 0
        set(value) = with(binding) {
            headerParentLayout.backgroundColor = value
            okButton.setBackgroundTint(value)
        }

    var headerTextColor: Int = 0
        set(value) = with(binding) {
            toolbar.setTitleTextColor(value)
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
            toolbar.menu.findItem(R.id.menuCloseAction)?.setVisible(value)
        }

    override fun setCancelable(flag: Boolean) {
        super.setCancelable(flag)
        hasDismissButton = flag
    }

    open fun onActionButton(actionCallback: (Boolean) -> Unit) {
        this.actionCallback = actionCallback
    }

    open fun createActionMenu(): Int {
        return R.menu.menu_framed_dialog
    }

    open fun prepareActionMenu(menu: Menu) {}

    open fun actionMenu(
        actionMenuCallback: MenuItem,
        itemId: Int,
    ) {
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