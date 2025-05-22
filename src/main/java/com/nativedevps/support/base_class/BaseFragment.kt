package com.nativedevps.support.base_class

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.custom_views.ProgressDialog
import org.jetbrains.anko.toast


abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
    private val inflate: Inflate<VB>,
    private val viewModelClass: Class<VM>,
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    private val bindingLiveData = MutableLiveData<VB>()
    protected val viewModel: VM by lazy { ViewModelProvider(this).get(viewModelClass) }
    private val baseViewModel: BaseViewModel get() = viewModel
    private val currentActivity get() = (activity as BaseActivity<*, *>)
    private var progressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (bindingLiveData.value == null || !preserveState()) {
            _binding = inflate.invoke(requireActivity().layoutInflater, container, false).also {
                bindingLiveData.value = it
            }

            onInit(inflater, container, savedInstanceState)
            onInit(savedInstanceState)
        } else {
            _binding = bindingLiveData.value
        }
        return binding.root
    }

    private fun initObserver() {}

    override fun onDestroyView() {
        super.onDestroyView()
        progressDialog?.dismiss()
        _binding = null
    }

    open fun onInit(savedInstanceState: Bundle?) {
        //noop
    }

    open fun onInit(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) {
        //noop
    }

    fun toast(string: String) {
        activity?.toast(string)
    }

    open fun preserveState(): Boolean {
        return false
    }
}