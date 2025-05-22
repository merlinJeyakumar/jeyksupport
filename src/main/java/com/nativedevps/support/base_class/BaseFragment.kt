package com.nativedevps.support.base_class

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.custom_views.ProgressDialog
import nativedevps.support.R
import org.jetbrains.anko.toast


abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
    private val inflate: Inflate<VB>,
    private val viewModelClass: Class<VM>,
) : Fragment() {

    private var dynamicView: View? = null
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
    }

    open fun initObserver() = with(viewModel) {

    }

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

    // Method to add a dynamic view
    private fun addDynamicView() {
        // Find the container in the layout (assumes a LinearLayout with ID view_container)
        val container = binding.root.findViewById<LinearLayout>(R.id.loadingViewContainer)
            ?: return // Handle case where container is not found

        // Create a dynamic view (e.g., a Button)
        dynamicView = Button(requireContext()).apply {
            text = "Dynamic Button"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                // Remove the dynamic view when clicked
                removeDynamicView()
            }
        }

        // Add the dynamic view to the container
        container.addView(dynamicView)
    }

    // Method to remove the dynamic view
    protected fun removeDynamicView() {
        val container = binding.root.findViewById<LinearLayout>(R.id.loadingViewContainer)
        dynamicView?.let {
            container?.removeView(it)
            dynamicView = null // Clear reference
        }
    }

    // Optional: Method to re-add the dynamic view if needed
    protected fun reAddDynamicView() {
        if (dynamicView == null) {
            addDynamicView()
        }
    }

    open fun preserveState(): Boolean {
        return false
    }
}