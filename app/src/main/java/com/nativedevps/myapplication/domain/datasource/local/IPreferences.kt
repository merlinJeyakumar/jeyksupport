package com.nativedevps.myapplication.domain.datasource.local

import com.nativedevps.myapplication.domain.model.currency.CurrencyModel
import com.nativedevps.myapplication.domain.model.user.UserResponseModel
import kotlinx.coroutines.flow.Flow

interface IPreferences {
    suspend fun updateUser(userResponseModel: UserResponseModel)
    val userModel:Flow<UserResponseModel>
}