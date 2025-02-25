package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nativedevps.support.base_class.dialog.FramedAlertDialog
import nativedevps.support.R
import nativedevps.support.databinding.DialogListBinding
import nativedevps.support.databinding.ItemSimpleListViewBinding

class ListDialog(
    activeContext: Context,
    private val allowSearch: Boolean = true,
) : FramedAlertDialog<DialogListBinding>(
    context = activeContext,
    bindingFactory = DialogListBinding::inflate,
    theme = R.style.TransparentDialogStyle
) {
    var checkedList: List<ArrayDrawableListViewAdapter.ItemModel> = listOf()
    var checkable: Boolean = false
    private var onItemSelectedCallback: ((ArrayDrawableListViewAdapter.ItemModel, longPress: Boolean) -> Unit?)? =
        null
    private var onItemsSelectedCallback: ((List<ArrayDrawableListViewAdapter.ItemModel>, longPress: Boolean) -> Unit?)? =
        null

    private var menu: Menu? = null
    private val searchActionMenu get() = menu?.findItem(R.id.menuSearchAction)

    private fun initListener() = with(childBinding) {
        ///todo
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

    fun setInitialCheckedItems(list: List<String>) {
        unfilteredList.filter { list.contains(it.item) }.forEach { it.isChecked = true }
        updateList(unfilteredList)
    }

    fun updateList(list: List<ArrayDrawableListViewAdapter.ItemModel>) = with(childBinding) {
        itemsListView.adapter = ArrayDrawableListViewAdapter(
            context,
            list,
            checkable,
            object : ArrayDrawableListViewAdapter.ArrayViewHolder.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    adapter?.items?.getOrNull(position)?.let {
                        onItemSelectedCallback?.invoke(it, false)
                        onItemsSelectedCallback?.invoke(listOf(it), false)
                    }
                }

                override fun onItemLongClick(position: Int) {
                    adapter?.items?.getOrNull(position)?.let {
                        onItemSelectedCallback?.invoke(it, true)
                        onItemsSelectedCallback?.invoke(listOf(it), true)
                    }
                }

                override fun onItemsChecked(list: List<ArrayDrawableListViewAdapter.ItemModel>) {
                    onItemsSelectedCallback?.invoke(list, true)
                    checkedList = list
                }

            }
        )
    }

    fun setSearchAction(boolean: Boolean) {
        searchActionMenu?.setVisible(boolean)
    }

    var adapter: ArrayDrawableListViewAdapter? = null

    var message = ""
        set(text) = with(childBinding) {
            messageAppCompatTextView.setText(text)
        }

    @Deprecated("use list callback instead")
    fun onItemSelected(callback: (ArrayDrawableListViewAdapter.ItemModel, longPress: Boolean) -> Unit) {
        this.onItemSelectedCallback = callback
    }

    fun onItemsSelected(callback: (List<ArrayDrawableListViewAdapter.ItemModel>, longPress: Boolean) -> Unit) {
        this.onItemsSelectedCallback = callback
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
        private val checkable: Boolean,
        private var itemClickListener: ArrayViewHolder.OnItemClickListener? = null,
    ) : RecyclerView.Adapter<ArrayDrawableListViewAdapter.ArrayViewHolder>() {

        data class ItemModel(val position: Int, val item: String, var isChecked: Boolean = false)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArrayViewHolder {
            return ArrayViewHolder(
                ItemSimpleListViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                appContext,
                checkable,
                items,
                itemClickListener
            )
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ArrayViewHolder, position: Int) {
            holder.bind(items[position])
        }

        class ArrayViewHolder(
            private val binding: ItemSimpleListViewBinding,
            private val appContext: Context,
            private val checkable: Boolean = false,
            private val items: List<ItemModel>,
            private val itemClickListener: OnItemClickListener?,
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: ItemModel) = with(binding) {
                binding.text1.text = item.item
                if (checkable.not()) {
                    binding.text1.isClickable = false
                    binding.text1.buttonDrawable =
                        ContextCompat.getDrawable(appContext, android.R.color.transparent)
                }
                val currentItem = items[position]

                text1.isChecked = currentItem.isChecked
                text1.text = currentItem.item
                itemView.setOnClickListener {
                    itemClickListener?.onItemClick(currentItem.position)
                }

                itemView.setOnLongClickListener {
                    itemClickListener?.onItemLongClick(currentItem.position)
                    return@setOnLongClickListener true
                }

                binding.text1.addOnCheckedStateChangedListener { checkBox, state ->
                    if (checkable) {
                        currentItem.isChecked = checkBox.isChecked
                        itemClickListener?.onItemsChecked(items.filter { it.isChecked })
                    }
                }
            }

            interface OnItemClickListener {
                fun onItemClick(position: Int)
                fun onItemLongClick(position: Int)
                fun onItemsChecked(list: List<ItemModel>)
            }
        }
    }
}