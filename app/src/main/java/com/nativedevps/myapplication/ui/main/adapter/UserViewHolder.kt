package com.nativedevps.myapplication.ui.main.adapter

import com.nativedevps.myapplication.databinding.ItemUserBinding
import com.nativedevps.myapplication.domain.model.user_list.UsersListResponseModel
import com.nativedevps.support.base_class.BaseViewHolder

class UserViewHolder(
    private val binding: ItemUserBinding,
    selectionList: MutableList<Int>,
) : BaseViewHolder<UsersListResponseModel.Data, Int>(selectionList, binding.root) {


    override fun bind(position: Int, item: UsersListResponseModel.Data) = with(binding) {
        //todo:
    }
}