package com.nativedevps.myapplication.data.rest.user

import com.nativedevps.myapplication.domain.datasource.rest.UserApiService
import com.nativedevps.myapplication.domain.model.update.UserUpdateRequestModel
import com.nativedevps.myapplication.domain.model.user.UserResponseModel
import com.nativedevps.support.base_class.FlowUseCase
import com.nativedevps.support.coroutines.NetworkResult
import com.nativedevps.support.utility.usecase.emulate
import com.nativedevps.support.utility.usecase.emulateNetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Injecting ApiService
 * @return result with status of success/failure {@link NetworkResult}
 */
class UserUpdateUseCase @Inject constructor(
    private val exchangeApiService: UserApiService
) : FlowUseCase<UserUpdateRequestModel, UserResponseModel>() {

    override fun performAction(parameters: UserUpdateRequestModel): Flow<NetworkResult<UserResponseModel>> {
        return emulate {
            exchangeApiService.user(parameters.id).emulateNetworkCall()
        }
    }
}