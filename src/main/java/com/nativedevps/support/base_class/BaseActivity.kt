package com.nativedevps.support.base_class

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.custom_views.ProgressDialog
import com.nativedevps.support.inline.orElse
import com.nativedevps.support.utility.language.ContextWrapper
import com.nativedevps.support.utility.language.Utility.getDeviceLocale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    bindingFactory: Int,
    private val viewModelClass: Class<VM>,
) : AppCompatActivity() {
    protected val viewModel: VM by lazy { ViewModelProvider(this).get(viewModelClass) }
    private var progressDialog: ProgressDialog? = null

    protected val binding: B by contentView(bindingFactory)

    private lateinit var _binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        preInit()
        setContentView(binding.root)

        initObserver()
        onInit(savedInstanceState)
        viewModel.onCreate()
    }

    open fun preInit() {
    }

    private fun initObserver() {
        viewModel.liveDataProgressBar.observe(this) { triple ->
            if (triple.first) {
                progressDialog?.setProgress(triple.second).orElse {
                    progressDialog = ProgressDialog(this)
                    progressDialog?.setCancelable(false)
                    progressDialog?.setOnDismissListener {
                        progressDialog = null
                    }
                }
                progressDialog?.setMessage(triple.third)?.build()
            } else {
                progressDialog?.dismiss()
            }
        }
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
    }

    abstract fun onInit(savedInstanceState: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
        progressDialog?.dismiss()
    }

    fun runOnNewThread(callback: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            callback()
        }
    }

    fun CoroutineScope.runOnUiThread(callback: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            callback()
        }
    }

    open fun showProgressDialog(message: String = "loading..", progress: Int = -1) {
        Log.e("JK", "showProgressDialog")
        runOnUiThread {
            viewModel.liveDataProgressBar.value = Triple(true, progress, message)
        }
    }

    open fun hideProgressDialog() {
        Log.e("JK", "hideProgressDialog")
        runOnUiThread {
            viewModel.liveDataProgressBar.value = Triple(false, -1, "")
        }
    }

    override fun attachBaseContext(context: Context) {
        val lang = getLocale(context) ?: getDeviceLocale()?.language
        val contextWrapper = Locale(lang).let {
            Locale.setDefault(it)
            ContextWrapper.wrap(context, it)
        }
        super.attachBaseContext(contextWrapper)
    }

    open fun getLocale(context: Context): String? {
        return null
    }
}