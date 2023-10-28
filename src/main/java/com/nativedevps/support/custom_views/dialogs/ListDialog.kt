package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import com.nativedevps.support.base_class.dialog.FramedAlertDialog
import com.nativedevps.support.utility.view.ViewUtils.visibility
import nativedevps.support.R
import nativedevps.support.databinding.DialogListBinding
import nativedevps.support.databinding.ItemSimpleListViewBinding
import org.jetbrains.anko.layoutInflater

class ListDialog(
    activeContext: Context,
    private val allowSearch: Boolean,
) : FramedAlertDialog<DialogListBinding>(
    context = activeContext,
    bindingFactory = DialogListBinding::inflate,
    theme = R.style.TransparentDialogStyle
) {
    private var onItemSelectedCallback: ((ArrayDrawableListViewAdapter.ItemModel, longPress: Boolean) -> Unit?)? = null
    private var menu: Menu? = null
    private val searchActionMenu get() = menu?.findItem(R.id.menuSearchAction)

    private fun initListener() = with(childBinding) {
        itemsListView.setOnItemClickListener { parent, view, position, id ->
            val item = (itemsListView.adapter as? ArrayDrawableListViewAdapter)?.items?.get(
                position)
            item?.let { onItemSelectedCallback?.invoke(it,false) }
        }
        itemsListView.setOnItemLongClickListener { parent, view, position, id ->
            val item = (itemsListView.adapter as? ArrayDrawableListViewAdapter)?.items?.get(
                position)
            item?.let { onItemSelectedCallback?.invoke(it, true) }
            return@setOnItemLongClickListener true
        }
    }

    private fun initPreview() = with(binding) {
        hasNegativeButton = false
        hasPositiveButton = false
    }

    fun setList(list: List<String>) = with(childBinding) {
        unfilteredList = list.mapIndexed { index, s ->
            ArrayDrawableListViewAdapter.ItemModel(index, s)
        }
        updateList(unfilteredList)
    }

    fun updateList(list: List<ArrayDrawableListViewAdapter.ItemModel>) = with(childBinding) {
        itemsListView.adapter = ArrayDrawableListViewAdapter(context, list)
    }

    fun setSearchAction(boolean: Boolean) {
        searchActionMenu?.setVisible(boolean)
    }

    var message = ""
        set(text) = with(childBinding) {
            messageAppCompatTextView.setText(text)
        }

    fun onItemSelected(callback: (ArrayDrawableListViewAdapter.ItemModel, longPress: Boolean) -> Unit) {
        this.onItemSelectedCallback = callback
    }

    override fun onCreate() {
        super.onCreate()

        initListener()
        initPreview()
    }

    override fun containerLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            context.resources.getDimensionPixelOffset(com.intuit.sdp.R.dimen._150sdp)
        )
    }

    override fun createActionMenu(): Int {
        return R.menu.menu_list_dialog
    }

    private var unfilteredList = listOf<ArrayDrawableListViewAdapter.ItemModel>()
    override fun prepareActionMenu(menu: Menu) {
        this.menu = menu
        val searchView = (searchActionMenu?.actionView as? SearchView)
        searchView?.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean = with(childBinding) {
                    if (newText.isEmpty()) {
                        if (!searchView.isIconified()) {
                            searchView.setIconified(true);
                            searchView.onActionViewCollapsed()
                        }
                        updateList(unfilteredList)
                    } else {
                        val filteredList = unfilteredList.filter {
                            it.item.contains(newText, true)
                        }
                        updateList(filteredList)
                    }
                    return true
                }
            })
    }

    companion object {
        fun build(context: Context, allowSearch: Boolean = true): ListDialog {
            return ListDialog(context, allowSearch).also {
                it.show()
            }
        }
    }


    open class ArrayDrawableListViewAdapter(
        private var appContext: Context,
        var items: List<ItemModel>,
    ) : ArrayAdapter<ArrayDrawableListViewAdapter.ItemModel>(appContext,
        R.layout.item_simple_list_view,
        items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val binding =
                ItemSimpleListViewBinding.inflate(appContext.layoutInflater, parent, false)
            val currentItem = items[position]

            binding.text1.text = currentItem.item
            return binding.root
        }

        data class ItemModel(val position: Int, val item: String) {

        }
    }
}