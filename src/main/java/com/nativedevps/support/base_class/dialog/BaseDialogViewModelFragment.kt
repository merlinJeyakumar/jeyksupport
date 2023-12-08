package com.nativedevps.support.base_class.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nativedevps.support.base_class.Inflate
import nativedevps.support.R
import org.jetbrains.anko.toast

abstract class BaseDialogViewModelFragment<VB : ViewDataBinding, VM : ViewModel>(
    private val bindingFactory: Inflate<VB>,
    private val viewModelClass: Class<VM>,
) : AppCompatDialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    protected val viewModel: VM by lazy { ViewModelProvider(this).get(viewModelClass) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, theme())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingFactory.invoke(inflater, container, false)

        onInit(savedInstanceState)
        return binding.root
    }

    open fun onInit(savedInstanceState: Bundle?) {
    }

    fun toast(string: String) {
        activity?.toast(string)
    }

    open fun theme(): Int {
        return R.style.TransparentDialogStyle
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnCancelListener {
            toast("Cancelling")
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }
}