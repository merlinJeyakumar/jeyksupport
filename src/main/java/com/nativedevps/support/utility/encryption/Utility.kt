package com.nativedevps.support.utility.encryption

import com.google.crypto.tink.subtle.Base64
import com.nativedevps.support.utility.encryption.Utility.decryptAES
import com.nativedevps.support.utility.encryption.Utility.encryptAES
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object Utility {
    fun String.encryptAES(hash: String): Single<String> {
        return Single.create { singleEmitter ->
            val cipherTextIvMac: AesCbcWithIntegrity.CipherTextIvMac =
                AesCbcWithIntegrity.encrypt(
                    this,
                    AesCbcWithIntegrity.generateKeyFromPassword(
                        hash,
                        hash.toByteArray()
                    )
                )
            singleEmitter.onSuccess(cipherTextIvMac.toString())
        }
    }

    fun String.decryptAES(hash: String): Single<String> {
        return Single.create { singleEmitter ->
            val cipherTextIvMac = AesCbcWithIntegrity.CipherTextIvMac(this)
            singleEmitter.onSuccess(
                AesCbcWithIntegrity.decryptString(
                    cipherTextIvMac,
                    AesCbcWithIntegrity.generateKeyFromPassword(
                        hash,
                        hash.toByteArray()
                    )
                )
            )
        }
    }
}

fun String.encryptWithAes(hash: String): Flow<String> {
    return flow {
        emit(AESUtil.encrypt(this@encryptWithAes, hash))
    }
}

fun String.decryptWithAes(hash: String): Flow<String> {
    return flow {
        emit(AESUtil.decrypt(this@decryptWithAes, hash))
    }
}

fun String.encodeToBase64(): String {
    val bytes = this.toByteArray(Charsets.UTF_8)
    val encodedBytes = Base64.encode(
        bytes
    )
    return encodedBytes
}

fun String.decodeBase64(): String {
    val encodedBytes = Base64.decode(
        this
    )
    return String(encodedBytes, Charsets.UTF_8)
}