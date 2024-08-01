package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.nativedevps.support.base_class.dialog.BaseAlertDialog
import com.nativedevps.support.base_class.dialog.BaseDialogFragment
import com.nativedevps.support.model.LoaderProperties
import com.nativedevps.support.utility.view.ViewUtils.gone
import nativedevps.support.R
import nativedevps.support.databinding.DialogProgressBarBinding

class LoaderDialog(
    context: Context,
    private var lottieFile: Int? = null,
) : BaseDialogFragment<DialogProgressBarBinding>(
    bindingFactory = DialogProgressBarBinding::inflate
) {
    override fun onInit(savedInstanceState: Bundle?) {
        super.onInit(savedInstanceState)

        initListener()
        initPreview()
    }

    private fun initPreview() = with(binding) {
        progress = 0
        lottieFile?.let {
            lottieAnimationView.setAnimation(it)
            lottieAnimationView.playAnimation()
        }
    }

    private fun initListener() {
        //noop
    }

    var message: String = ""
        set(text) = with(binding) {
            messageAppCompatTextView.setText(text)
        }

    var progress: Int = 0
        set(value) = with(binding) {
            progress = value
        }

    fun setLoaderProperties(loaderProperties: LoaderProperties) {
        loaderProperties.progress = loaderProperties.progress
        setCancelable(loaderProperties.cancellable)
        message = loaderProperties.message
        //allowDuplicate(false)
    }

    override fun dismiss() = with(binding) {
        lottieAnimationView.cancelAnimation()
        super.dismiss()
    }

    override fun theme(): Int {
        return R.style.MaterialDialog_MatchParent
    }

    companion object {
        fun build(
            fragmentManager: FragmentManager,
            context: Context,
            lottieFile: Int? = null,
        ): LoaderDialog {
            return LoaderDialog(context, lottieFile).also {
                it.show(fragmentManager,"loader")
            }
        }
    }
}