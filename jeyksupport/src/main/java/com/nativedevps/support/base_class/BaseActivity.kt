package com.nativedevps.support.base_class

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
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

/*
* Provide basic signature for the activity
*
* Example usage
```
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main,
    MainViewModel::class.java
)
```
**/
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel>(
    private val bindingFactory: Int,
    private val viewModelClass: Class<VM>,
) : AppCompatActivity() {
    protected val binding: VB by contentView(bindingFactory)

    protected val viewModel: VM by lazy { ViewModelProvider(this).get(viewModelClass) }

    override fun onCreate(savedInstanceState: Bundle?) {

        preInit()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initObserver()
        onInit(savedInstanceState)
        viewModel.onCreate()
    }

    open fun preInit() {
    }

    private fun initObserver() {
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

    protected val pushNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            Log.v("NotificationPermission", "NotificationPermission $granted")
        }
}