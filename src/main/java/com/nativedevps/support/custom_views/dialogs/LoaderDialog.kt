package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.nativedevps.support.base_class.dialog.BaseDialogFragment
import com.nativedevps.support.model.LoaderProperties
import nativedevps.support.R
import nativedevps.support.databinding.DialogProgressBarBinding

class LoaderDialog(
    context: Context,
    private val loaderConstraints: LoaderConstraints,
) : BaseDialogFragment<DialogProgressBarBinding>(
    bindingFactory = DialogProgressBarBinding::inflate
) {
    private var onViewReadyCallback: ((LoaderDialog, DialogProgressBarBinding) -> Unit)? = null
    private var loaderProperties: LoaderProperties? = null
    override fun onInit(savedInstanceState: Bundle?) {
        super.onInit(savedInstanceState)

        initListener()
        initPreview()
    }

    private fun initPreview() = with(binding) {
        progress = 0
        canLottie = loaderConstraints.lottieFile != null
        if (loaderConstraints.lottieFile != null) {
            lottieAnimationView.setAnimation(
                loaderConstraints.lottieFile
            )
            lottieAnimationView.playAnimation()
        }
        cardView.setCardBackgroundColor(requireContext().resources.getColor(loaderConstraints.cardBackgroundColor))
        onViewReadyCallback?.invoke(this@LoaderDialog, binding)
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
        this@LoaderDialog.loaderProperties = loaderProperties
        loaderProperties.progress = loaderProperties.progress
        setCancelable(loaderProperties.cancellable)
        message = loaderProperties.message
        //allowDuplicate(false)
    }

    override fun theme(): Int {
        return R.style.MaterialDialog_MatchParent
    }

    fun onViewReady(callback: (LoaderDialog, DialogProgressBarBinding) -> Unit) {
        onViewReadyCallback  = callback
    }

    companion object {
        fun build(
            fragmentManager: FragmentManager,
            context: Context,
            loaderConstraints: LoaderConstraints,
        ): LoaderDialog {
            return LoaderDialog(context, loaderConstraints,).also {
                if (loaderConstraints.autoShowLoader) {
                    it.show(fragmentManager, "loader")
                }
            }
        }
    }

    data class LoaderConstraints(
        val message: String = "Loading",
        val lottieFile: Int? = null,
        val autoShowLoader: Boolean = true,
        val cardBackgroundColor:Int = R.color.dayNightWhite
    )
}