package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.ViewGroup
import com.nativedevps.support.base_class.dialog.FramedAlertDialog
import com.nativedevps.support.custom_views.ArrayDrawableListViewAdapter
import nativedevps.support.R
import nativedevps.support.databinding.DialogListBinding

class ListDialog<TY>(
    private val activeContext: Context,
) : FramedAlertDialog<DialogListBinding>(
    context = activeContext,
    bindingFactory = DialogListBinding::inflate,
    theme = R.style.TransparentDialogStyle
) {
    override fun preInit() {
    }

    private fun initListener() = with(binding) {
        //noop
    }

    private fun initPreview() = with(binding) {
        //noop
    }

    fun setList(list: List<TY>) = with(childBinding) {
        itemsListView.adapter = ArrayDrawableListViewAdapter(context, list)
    }

    var message = ""
        set(text) = with(childBinding) {
            messageAppCompatTextView.setText(text)
        }

    override fun onCreate() {
        super.onCreate()

        initListener()
        initPreview()
    }

    override fun containerLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            context.resources.getDimensionPixelOffset(R.dimen._150sdp)
        )
    }

    companion object {
        fun <T> build(context: Context): ListDialog<T> {
            return ListDialog<T>(context).also {
                it.show()
            }
        }
    }
}