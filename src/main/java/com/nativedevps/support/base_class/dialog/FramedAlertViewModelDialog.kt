package com.nativedevps.support.base_class.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.base_class.BaseViewModel
import com.nativedevps.support.base_class.Inflate
import com.nativedevps.support.utility.view.ViewUtils.setBackgroundTint
import com.nativedevps.support.utility.view.ViewUtils.visibility
import nativedevps.support.R
import nativedevps.support.databinding.DialogFramedBinding
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.layoutInflater

abstract class FramedViewModelDialog<VB : ViewBinding, VM : BaseViewModel>(
    private val context: Context,
    private val bindingFactory: (LayoutInflater) -> VB,
    private val viewModelClass: Class<VM>,
) : BaseDialogViewModelFragment<DialogFramedBinding, VM>(
    DialogFramedBinding::inflate,
    viewModelClass
) {

    lateinit var asdialog: Dialog

    private var _binding: ViewBinding = bindingFactory.invoke(context.layoutInflater)
    protected val childBinding: VB by lazy { _binding as VB }
    private var actionCallback: ((Boolean) -> Unit)? = null

    /*override fun preInit() {
        super.preInit()

        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.MainDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        asdialog = super.onCreateDialog(savedInstanceState)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.frameLayout.addView(childBinding.root, containerLayoutParams())

        initListener()
        initToolbar()
        initPreview()

        return binding.root
    }

    private fun initToolbar() = with(binding) {
        toolbar.setTitle(requireContext().resources.getString(R.string.app_name))
        toolbar.inflateMenu(createActionMenu())
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menuCloseAction) {
                onCancel(dialog)
            }
            return@setOnMenuItemClickListener true
        }
        prepareActionMenu(toolbar.menu)
    }

    private fun initPreview() {
        hasButton(true)
        hasNegativeButton(true)
        hasPositiveButton(true)
        negativeButtonText("Cancel")
        positiveButtonText("Ok")
    }

    private fun initListener() = with(binding) {
        okButton.setOnClickListener {
            actionButton(true)
        }
        cancelButton.setOnClickListener {
            actionButton(false)
        }
    }

    fun headerTitle(text: String) = with(binding) {
        toolbar.setTitle(text)
    }

    fun headerBackgroundColor(value: Int) = with(binding) {
        headerParentLayout.backgroundColor = value
        okButton.setBackgroundTint(value)
    }

    fun headerTextColor(color: Int) = with(binding) {
        toolbar.setTitleTextColor(color)
        okButton.setTextColor(color)
    }

    fun hasButton(boolean: Boolean) = with(binding) {
        buttons.visibility(boolean)
    }

    fun hasPositiveButton(value: Boolean) = with(binding) {
        okButton.visibility(value)
    }

    fun hasNegativeButton(value: Boolean) = with(binding) {
        cancelButton.visibility(value)
    }

    fun negativeButtonText(value: String = "Cancel") = with(binding) {
        cancelButton.setText(value)
    }

    fun positiveButtonText(value: String = "Ok") = with(binding) {
        okButton.setText(value)
    }

    fun hasDismissButton(value: Boolean = true) = with(binding) {
        toolbar.menu.findItem(R.id.menuCloseAction)?.setVisible(value)
    }

    override fun setCancelable(flag: Boolean) {
        super.setCancelable(flag)
        hasDismissButton(flag)
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

    open fun onCancel(dialog: Dialog?) {

    }
}