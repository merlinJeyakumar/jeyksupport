package com.nativedevps.support.base_class

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import org.jetbrains.anko.toast


abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflate: Inflate<VB>,
    private val viewModelClass: Class<VM>,
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    protected val viewModel: VM by lazy { ViewModelProvider(this).get(viewModelClass) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)

        onInit(inflater, container, savedInstanceState)
        onInit(savedInstanceState)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun onInit(savedInstanceState: Bundle?) {

    }

    open fun onInit(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) {

    }

    fun toast(string: String) {
        activity?.toast(string)
    }
}