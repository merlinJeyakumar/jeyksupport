package com.nativedevps.support.utility.encryption

import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.nativedevps.support.utility.debugging.Log
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.cert.CertificateFactory

class SecurityUtil(private val context: Context) {
    private val KEYSET_NAME = "master_keyset"
    private val PREFERENCE_FILE = "master_key_preference"
    private val MASTER_KEY_URI = "android-keystore://master_key"
    private var aead: Aead

    init {
        AeadConfig.register()
        aead = AndroidKeysetManager.Builder()
            .withSharedPref(context, KEYSET_NAME, PREFERENCE_FILE)
            .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle
            .getPrimitive(Aead::class.java)
    }

    private val TYPE_STRING = 1
    private val TYPE_BOOLEAN = 2
    private val TYPE_INT = 3
    private val TYPE_LONG = 4
    private val TYPE_DOUBLE = 5

    fun encrypt(data: Any): String {
        val type: Int
        val dataByteArray: ByteArray = when (data) {
            is String -> {
                type = TYPE_STRING
                data.toByteArray(StandardCharsets.UTF_8)
            }

            is Boolean -> {
                type = TYPE_BOOLEAN
                ByteBuffer.allocate(1).put(if (data) 1.toByte() else 0.toByte()).array()
            }

            is Number -> {
                type = when (data) {
                    is Int -> TYPE_INT
                    is Double -> TYPE_DOUBLE
                    is Long -> TYPE_LONG
                    else -> throw IllegalArgumentException("Data type not supported for encryption.")
                }
                numberToByteArray(data)
            }

            else -> throw IllegalArgumentException("Data type not supported for encryption.")
        }

        val encryptedData = aead.encrypt(dataByteArray, null)
        val encryptedWithType = ByteBuffer.allocate(encryptedData.size + 1).apply {
            put(type.toByte())
            put(encryptedData)
        }.array()

        return Base64.encodeToString(encryptedWithType, Base64.DEFAULT)
            .replace("\\r\\n|\\r|\\n".toRegex(), "")
    }

    fun decrypt(data: String): Any {
        if (data.isEmpty()) {
            return ""
        }

        val encryptedWithType =
            Base64.decode(data.replace("\\r\\n|\\r|\\n".toRegex(), ""), Base64.DEFAULT)
        val type = encryptedWithType[0].toInt()

        val encryptedData = encryptedWithType.copyOfRange(1, encryptedWithType.size)
        val decryptedBytes = aead.decrypt(encryptedData, null)

        return when (type) {
            TYPE_STRING -> String(decryptedBytes, StandardCharsets.UTF_8)
            TYPE_BOOLEAN -> byteArrayToBoolean(decryptedBytes)
            TYPE_INT -> byteArrayToInt(decryptedBytes)
            TYPE_LONG -> byteArrayToLong(decryptedBytes)
            TYPE_DOUBLE -> byteArrayToDouble(decryptedBytes)
            else -> throw IllegalArgumentException("Unsupported data type during decryption.")
        }
    }

    private fun numberToByteArray(number: Number): ByteArray {
        return when (number) {
            is Int -> ByteBuffer.allocate(4).putInt(number).array()
            is Double -> ByteBuffer.allocate(8).putDouble(number).array()
            is Long -> ByteBuffer.allocate(8).putLong(number).array()
            else -> throw IllegalArgumentException("Data type not supported for encryption.")
        }
    }

    private fun byteArrayToBoolean(byteArray: ByteArray): Boolean {
        return byteArray[0] == 1.toByte()
    }

    private fun byteArrayToDouble(byteArray: ByteArray): Double {
        return ByteBuffer.wrap(byteArray).double
    }

    private fun byteArrayToLong(byteArray: ByteArray): Long {
        return ByteBuffer.wrap(byteArray).long
    }

    private fun byteArrayToInt(byteArray: ByteArray): Int {
        return ByteBuffer.wrap(byteArray).int
    }

    fun isValidKeystore(): Boolean {
        return getFingerPrint() != null
    }

    fun getFingerPrint(): String? {
        try {
            val packageName = context.applicationContext.packageName
            val packageManager = context.packageManager

            // Handle API compatibility for signing information
            val packageInfo =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    packageManager.getPackageInfo(
                        packageName,
                        PackageManager.GET_SIGNING_CERTIFICATES
                    )
                } else {
                    packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
                }

            val signatures =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                    packageInfo.signingInfo.apkContentsSigners
                } else {
                    packageInfo.signatures
                }

            if (signatures.isEmpty()) {
                Log.e("Keystore Info", "No signing certificates found!")
                return null
            }

            for (signature in signatures) {
                try {
                    // Parse the certificate
                    val certFactory = CertificateFactory.getInstance("X.509")
                    val cert =
                        certFactory.generateCertificate(signature.toByteArray().inputStream())

                    // Compute SHA-256 hash
                    val messageDigest = try {
                        MessageDigest.getInstance("SHA-256")
                    } catch (e: Exception) {
                        Log.e("Keystore Info", "SHA-256 not supported: ${e.message}")
                        continue
                    }

                    val sha256Hash = messageDigest.digest(cert.encoded)
                    val sha256Base64 = com.google.crypto.tink.subtle.Base64.encodeToString(
                        sha256Hash,
                        com.google.crypto.tink.subtle.Base64.DEFAULT
                    )

                    // Log the keystore certificate hash
                    Log.d("Keystore Info", "SHA-256 Certificate: $sha256Base64")
                    return sha256Base64
                } catch (e: Exception) {
                    Log.e("Keystore Info", "Failed to parse certificate: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.e("Keystore Info", "Failed to retrieve keystore info: ${e.message}")
        }
        return null
    }

}