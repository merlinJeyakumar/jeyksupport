package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import com.nativedevps.support.base_class.dialog.FramedAlertDialog
import com.nativedevps.support.custom_views.ArrayDrawableListViewAdapter
import nativedevps.support.databinding.DialogListBinding

@ExperimentalStdlibApi
class ListDialog<TY>(
    private val activeContext: Context,
) : FramedAlertDialog<DialogListBinding>(
    context = activeContext,
    bindingFactory = DialogListBinding::inflate
) {
    override fun preInit() {
    }

    /*var bindingCallback: (binding: DialogSimpleListBinding, item: TY) -> Unit = { binding, item ->
        //(binding as ItemListBinding).text1.setText(item as String)
    }*/

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
            ///messageAppCompatTextView.setText(text)
        }

    override fun onCreate() {
        super.onCreate()

        initListener()
        initPreview()
    }

    companion object {
        fun <T> build(context: Context): ListDialog<T> {
            return ListDialog<T>(context).also {
                it.show()
            }
        }
    }
}