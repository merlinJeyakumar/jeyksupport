package com.nativedevps.support.utility.networking


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
class ErrorBodyModel(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)