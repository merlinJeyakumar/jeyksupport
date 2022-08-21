package com.nativedevps.support.base_class

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.custom_views.ProgressDialog
import com.nativedevps.support.inline.orElse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast


abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(
    private val inflate: Inflate<VB>,
    private val viewModelClass: Class<VM>,
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    protected val viewModel: VM by lazy { ViewModelProvider(this).get(viewModelClass) }
    private val baseViewModel: BaseViewModel get() = viewModel
    private val currentActivity get() = (activity as BaseActivity<*, *>)
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflate.invoke(inflater, container, false)

        initObserver()
        onInit(inflater, container, savedInstanceState)
        onInit(savedInstanceState)
        return binding.root
    }

    open fun initObserver() {
        /*activity?.let {
            baseViewModel.liveDataProgressBar.observe(currentActivity) { loaderProperties ->
                if (loaderProperties.show) {
                    progressDialog?.setProgress(loaderProperties.progress).orElse {
                        progressDialog = ProgressDialog(currentActivity)
                        progressDialog?.setCancelable(loaderProperties.cancellable)
                        progressDialog?.setOnCancelListener {
                            baseViewModel.onProgressDialogCancelled()
                        }
                        progressDialog?.setOnDismissListener {
                            progressDialog = null
                        }
                    }
                    progressDialog?.setMessage(loaderProperties.message)?.build()
                } else {
                    progressDialog?.dismiss()
                }
            }
        }*/
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

    open fun showProgressDialog(message: String = "loading..", progress: Int = -1) {
        viewModel.showProgressDialog(message, progress)
    }

    open fun hideProgressDialog() {
        viewModel.hideProgressDialog()
    }

    open fun runOnNewThread(callback: suspend CoroutineScope.() -> Unit) {
        viewModel.runOnNewThread(callback)
    }

    fun CoroutineScope.runOnUiThread(callback: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            callback()
        }
    }

}