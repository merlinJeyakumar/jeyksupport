package com.nativedevps.support.utility.encryption

import android.util.Base64
import com.google.crypto.tink.Aead
import java.nio.charset.StandardCharsets
import java.nio.ByteBuffer

class SecurityUtil(private val aead: Aead) {

    fun encrypt(data: Any): String {
        val dataByteArray = when (data) {
            is String -> data.toByteArray(StandardCharsets.UTF_8)
            is Boolean -> ByteBuffer.allocate(1).put(if (data) 1.toByte() else 0.toByte()).array()
            is Number -> numberToByteArray(data)
            else -> throw IllegalArgumentException("Data type not supported for encryption.")
        }

        val encrypted = aead.encrypt(dataByteArray, null)
        return Base64.encodeToString(encrypted, Base64.DEFAULT).replace("\\r\\n|\\r|\\n".toRegex(), "")
    }

    fun decrypt(data: String): Any {
        if (data.isEmpty()) return ""

        val encryptedBytes = Base64.decode(data.replace("\\r\\n|\\r|\\n".toRegex(), ""), Base64.DEFAULT)
        val decryptedBytes = aead.decrypt(encryptedBytes, null)

        return when (byteArrayToType(decryptedBytes)) {
            is Boolean -> byteArrayToBoolean(decryptedBytes)
            is Double -> byteArrayToDouble(decryptedBytes)
            is Long -> byteArrayToLong(decryptedBytes)
            is Int -> byteArrayToInt(decryptedBytes)
            is String -> String(decryptedBytes, StandardCharsets.UTF_8)
            else -> throw IllegalArgumentException("Data type not supported for decryption.")
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

    private fun byteArrayToType(byteArray: ByteArray): Any {
        return when (byteArray.size) {
            1 -> byteArrayToBoolean(byteArray)
            4 -> byteArrayToInt(byteArray)
            8 -> {
                val longValue = ByteBuffer.wrap(byteArray).long
                val doubleValue = longValue.toDouble()
                if (doubleValue == longValue.toDouble()) longValue else doubleValue
            }
            else -> throw IllegalArgumentException("Unsupported byte array size for numeric conversion.")
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
}