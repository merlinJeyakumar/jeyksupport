package com.nativedevps.support.base_class

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nativedevps.support.R
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.toast

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseDialogFragment<VB : ViewBinding, VM : ViewModel>(
    private val bindingFactory: Inflate<VB>,
    private val viewModelClass: Class<VM>,
) : DialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    protected val viewModel: VM by lazy { ViewModelProvider(this).get(viewModelClass) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    fun theme(): Int {
        return R.style.TransparentDialogStyle
    }
}