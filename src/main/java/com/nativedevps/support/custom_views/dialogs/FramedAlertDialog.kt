package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.base_class.dialog.BaseAlertDialog
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

    override fun onCreate() {
        binding.frameLayout.addView(childBinding.root)

        initListener()
    }

    private fun initListener() {
        binding.closeAppCompatImageView.setOnClickListener {
            dismiss()
        }
    }

    var headerTitle: String = ""
        set(text) = with(binding) {
            frameTitleAppCompatTextView.setText(text)
        }

    var headerBackgroundColor: Int = 0
        set(value) = with(binding) {
            headerParentLayout.backgroundColor = value
        }

    var headerTextColor: Int = 0
        set(value) = with(binding) {
            frameTitleAppCompatTextView.textColor = value
        }
}