package com.nativedevps.support.custom_views

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.nativedevps.support.utility.view.ViewUtils.setLeftDrawable
import nativedevps.support.R
import nativedevps.support.databinding.ItemSimpleListViewBinding
import org.jetbrains.anko.layoutInflater

/**
 * support string based list and left-drawable, string based lists
 * type should be Pair<Int, String> or Primitive type
 **/
class ArrayDrawableListViewAdapter<T>(
    private var appContext: Context,
    open var items: List<T>,
) : ArrayAdapter<T>(appContext, R.layout.item_simple_list_view, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSimpleListViewBinding.inflate(appContext.layoutInflater, parent, false)
        val currentItem = items[position]
        if (currentItem is Pair<*, *>) {
            (currentItem as Pair<Int, String>).let {
                binding.text1.setLeftDrawable(it.first)
                binding.text1.text = it.second
            }
        } else {
            binding.text1.text = currentItem.toString()
        }
        return binding.root
    }
}