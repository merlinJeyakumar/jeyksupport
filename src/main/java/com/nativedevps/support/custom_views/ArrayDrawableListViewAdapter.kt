package com.nativedevps.support.custom_views

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.nativedevps.support.utility.view.ViewUtils.setLeftDrawable
import nativedevps.support.R
import nativedevps.support.databinding.ItemSimpleListViewBinding

/**
 * support string based list and left-drawable, string based lists
 * type should be Pair<Int, String> or Primitive type
 **/
class ArrayDrawableListViewAdapter<T>(
    var activity: Activity,
    var items: List<T>,
) : ArrayAdapter<T>(activity, R.layout.item_simple_list_view, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSimpleListViewBinding.inflate(activity.layoutInflater, parent, false)
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