package com.nativedevps.myapplication.data.rest.user

import com.nativedevps.myapplication.domain.datasource.rest.UserApiService
import com.nativedevps.myapplication.domain.model.registration.RegisterRequestModel
import com.nativedevps.myapplication.domain.model.registration.RegisterResponseModel
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
class RegisterUserUseCase @Inject constructor(
    private val exchangeApiService: UserApiService
) : FlowUseCase<RegisterRequestModel, RegisterResponseModel>() {

    override fun performAction(parameters: RegisterRequestModel): Flow<NetworkResult<RegisterResponseModel>> {
        return emulate {
            exchangeApiService.register(parameters).emulateNetworkCall()
        }
    }
}