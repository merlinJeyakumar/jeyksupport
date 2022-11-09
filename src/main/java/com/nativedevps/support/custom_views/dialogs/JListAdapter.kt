package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.utility.debugging.Log
import org.jetbrains.anko.layoutInflater

/**
 * support string based list and left-drawable, string based lists
 * type should be Pair<Int, String> or Primitive type
 **/
class JListAdapter<T, B>(
    appContext: Context,
    private val items: List<T>,
    layoutId: Int,
    bindingFactory: ((LayoutInflater) -> B),
    private var bindView: ((binding: B,item:T) -> Unit)?,
) : ArrayAdapter<T>(appContext, layoutId, items) {

    private var _binding: B = bindingFactory.invoke(context.layoutInflater)
    private val binding: B by lazy { _binding as B }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        /*val binding = ItemSimpleListViewBinding.inflate(context.layoutInflater, parent, false)
        val currentItem = items[position]
        if (currentItem is Pair<*, *>) {
            (currentItem as Pair<Int, String>).let {
                binding.text1.setLeftDrawable(it.first)
                binding.text1.text = it.second
            }
        } else {
            binding.text1.text = currentItem.toString()
        }
        initBinding()*/
        bindView?.invoke(binding, items[position])
        Log.e("binding", "$binding")
        return (binding as ViewBinding).root
    }
}