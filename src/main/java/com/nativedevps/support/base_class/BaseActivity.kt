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
import com.nativedevps.support.model.LoaderProperties
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

    protected val binding: B by contentView(bindingFactory)

    private lateinit var _binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        preInit()
        initBinding()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initObserver()
        onInit(savedInstanceState)
        viewModel.onCreate()
    }

    open fun preInit() {
    }

    open fun initObserver() {
        //todo: define own inheritance of this class and handle manually
    }

    private fun initBinding() {
        binding.lifecycleOwner = this
    }

    abstract fun onInit(savedInstanceState: Bundle?)

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