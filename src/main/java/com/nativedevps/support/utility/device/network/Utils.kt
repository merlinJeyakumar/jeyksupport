package com.nativedevps.support.utility.device.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkInfo
import android.os.Build


@SuppressLint("MissingPermission")
fun isInternetConnected(context: Context): Boolean {
    val connectivity = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivity != null) {
        val nInfo = connectivity.activeNetworkInfo
        if (nInfo != null) {
            //do your thing
            if (nInfo.type == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true
            } else if (nInfo.type == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true
            }
        }
        return false
    }
    return false
}


@SuppressLint("MissingPermission")
fun Context.isInternetAvailable(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var netInfo: NetworkInfo? = null
    netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnected && netInfo.isAvailable
}

@SuppressLint("MissingPermission")
fun Context.isConnectedToMobileData(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(TRANSPORT_CELLULAR)
    } else {
        val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        return networkInfo.type == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnected
    }
}

@SuppressLint("MissingPermission")
fun Context.isConnectedToWifi(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities =
            connectivityManager.getNetworkCapabilities(network)
                ?: return false
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    } else {
        val networkInfo =
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                ?: return false
        networkInfo.isConnected
    }
}
