package com.nativedevps.myapplication.ui.main

import android.app.Application
import com.nativedevps.myapplication.data.rest.user.UserListRetrievalUseCase
import com.nativedevps.myapplication.domain.datasource.local.IPreferences
import com.nativedevps.myapplication.domain.model.user_list.UserListRequestModel
import com.nativedevps.myapplication.domain.model.user_list.UsersListResponseModel
import com.nativedevps.support.base_class.BaseViewModel
import com.nativedevps.support.coroutines.ErrorApiResult
import com.nativedevps.support.coroutines.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val userListRetrievalUseCase: UserListRetrievalUseCase,
    private val preferences: IPreferences
) : BaseViewModel(application) {

    val currencies get() = preferences.userModel //flow collector of persistence to retrieve currencies

    override fun onCreate() {
        //noop
    }

    /*
    * Subscribing currencies from rest api and listening for success/error result
    * */
    fun retrieveUserList(): Flow<NetworkResult<UsersListResponseModel>> {
        return userListRetrievalUseCase.invoke(
            UserListRequestModel(
                1, 10
            )
        ).onEach {
            if (it is ErrorApiResult) {
                toast(it.message)
            }
        }
    }
}