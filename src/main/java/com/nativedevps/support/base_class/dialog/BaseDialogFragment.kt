package com.nativedevps.support.base_class.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.base_class.Inflate
import nativedevps.support.R
import org.jetbrains.anko.toast

abstract class BaseDialogFragment<VB : ViewBinding> constructor(
    private val bindingFactory: Inflate<VB>
) : AppCompatDialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureDialog(dialog)
        setStyle(STYLE_NO_TITLE, theme())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingFactory.invoke(inflater, container, false)

        onInit(savedInstanceState)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    open fun onInit(savedInstanceState: Bundle?) {
    }

    fun toast(string: String) {
        activity?.toast(string)
    }

    open fun theme(): Int {
        return R.style.TransparentDialogStyle
    }

    open fun configureDialog(dialog: Dialog?) {
        dialog?.getWindow()?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            requestFeature(Window.FEATURE_NO_TITLE);
        }
    }
}