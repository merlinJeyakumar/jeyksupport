package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.LayoutInflater
import com.nativedevps.support.base_class.dialog.FramedAlertDialog
import nativedevps.support.R
import nativedevps.support.databinding.DialogSimpleListBinding
import nativedevps.support.databinding.ItemListBinding

@ExperimentalStdlibApi
class ListDialog<BI, TY>(
    private val activeContext: Context,
    private val bindingFactory: ((LayoutInflater) -> BI)
) : FramedAlertDialog<DialogSimpleListBinding>(
    context = activeContext,
    bindingFactory = DialogSimpleListBinding::inflate,
    theme = R.style.FullScreenDialogStyle
) {
    override fun preInit() {

    }

    var bindingCallback: (binding: BI, item: TY) -> Unit = { binding, item ->
        //(binding as ItemListBinding).text1.setText(item as String)
    }

    private fun initListener() = with(binding) {
        //noop
    }

    private fun initPreview() = with(binding) {
        //noop
    }

    fun setList(list: List<TY>) = with(childBinding) {
        val adapter = JListAdapter(
            appContext = activeContext,
            items = list,
            bindingFactory = bindingFactory,
            bindView = bindingCallback
        )
        itemsListView.adapter = adapter
    }

    var itemLayoutId: Int = R.layout.item_list
        set(value) {
            field = value
        }
        get() = R.layout.item_list

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
        fun <B, T> build(
            context: Context,
            bindingFactory: ((LayoutInflater) -> B),
        ): ListDialog<B, T> {
            return ListDialog<B, T>(context, bindingFactory).also {
                it.show()
            }
        }

        fun build(context: Context): ListDialog<ItemListBinding, String> {
            return ListDialog<ItemListBinding, String>(context, ItemListBinding::inflate).also {
                it.show()
            }
        }
    }
}