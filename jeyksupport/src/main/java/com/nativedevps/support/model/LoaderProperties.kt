package com.nativedevps.support.model

class LoaderProperties(
    var show: Boolean,
    var message: String = "",
    var progress: Int = -1,
    var cancellable: Boolean = false,
    var lottieFile: Int? = null,
)