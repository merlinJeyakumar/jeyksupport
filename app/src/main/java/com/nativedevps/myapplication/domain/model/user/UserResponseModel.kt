package com.nativedevps.myapplication.domain.model.user


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.nativedevps.myapplication.domain.model.user_list.UsersListResponseModel

@Keep
data class UserResponseModel(
    @SerializedName("data")
    val `data`: UsersListResponseModel.Data,
    @SerializedName("support")
    val support: Support
) {
    @Keep
    data class Support(
        @SerializedName("text")
        val text: String,
        @SerializedName("url")
        val url: String
    )
}