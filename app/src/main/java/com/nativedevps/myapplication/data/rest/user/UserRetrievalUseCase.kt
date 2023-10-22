package com.nativedevps.myapplication.data.rest.user

import com.nativedevps.myapplication.domain.datasource.rest.UserApiService
import com.nativedevps.myapplication.domain.model.update.UpdateResponseModel
import com.nativedevps.myapplication.domain.model.user.UserRequestModel
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
class UserRetrievalUseCase @Inject constructor(
    private val exchangeApiService: UserApiService
) : FlowUseCase<UserRequestModel, UpdateResponseModel>() {

    override fun performAction(parameters: UserRequestModel): Flow<NetworkResult<UpdateResponseModel>> {
        return emulate {
            exchangeApiService.updateWithPut(parameters.id).emulateNetworkCall() //put method
            exchangeApiService.updateWithPatch(parameters.id).emulateNetworkCall() //patch method
        }
    }
}