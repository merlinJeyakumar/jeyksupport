package com.nativedevps.support.utility.encryption

import io.reactivex.rxjava3.core.Single

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