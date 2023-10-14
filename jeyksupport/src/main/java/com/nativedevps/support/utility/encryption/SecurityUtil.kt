package com.nativedevps.support.utility.encryption

import android.util.Base64
import com.google.crypto.tink.Aead
import java.nio.charset.StandardCharsets
import java.nio.ByteBuffer

class SecurityUtil(private val aead: Aead) {

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

        return Base64.encodeToString(encryptedWithType, Base64.DEFAULT).replace("\\r\\n|\\r|\\n".toRegex(), "")
    }

    fun decrypt(data: String): Any {
        if (data.isEmpty()) {
            return ""
        }

        val encryptedWithType = Base64.decode(data.replace("\\r\\n|\\r|\\n".toRegex(), ""), Base64.DEFAULT)
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
    }}