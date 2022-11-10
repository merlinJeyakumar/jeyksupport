package com.nativedevps.support.custom_views.dialogs

import android.content.Context
import android.view.Menu
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.nativedevps.support.base_class.dialog.FramedAlertDialog
import com.nativedevps.support.custom_views.ArrayDrawableListViewAdapter
import nativedevps.support.R
import nativedevps.support.databinding.DialogListBinding

class ListDialog(
    activeContext: Context,
) : FramedAlertDialog<DialogListBinding>(
    context = activeContext,
    bindingFactory = DialogListBinding::inflate,
    theme = R.style.TransparentDialogStyle
) {

    private fun initListener() = with(binding) {
        //noop
    }

    private fun initPreview() = with(binding) {
        //noop
    }

    fun setList(list: List<String>) = with(childBinding) {
        unfilteredList = list
        updateList(list)
    }

    fun updateList(list: List<String>) = with(childBinding) {
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

    override fun createActionMenu(): Int {
        return R.menu.menu_list_dialog
    }

    private var unfilteredList = listOf<String>()
    override fun prepareActionMenu(menu: Menu) {
        val searchView = (menu.findItem(R.id.menuSearchAction).actionView as? SearchView)

        searchView?.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean = with(childBinding) {
                    if (newText.isEmpty()) {
                        binding.toolbar.collapseActionView()
                        updateList(unfilteredList)
                    } else {
                        val filteredList = unfilteredList.filter {
                            it.contains(newText, true)
                        }
                        updateList(filteredList)
                    }
                    return true
                }
            })
    }

    companion object {
        fun build(context: Context): ListDialog {
            return ListDialog(context).also {
                it.show()
            }
        }
    }
}