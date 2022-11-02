package com.nativedevps.support.utility.encryption

import android.util.Base64
import com.google.crypto.tink.Aead
import java.nio.charset.StandardCharsets

class SecurityUtil(private val aead: Aead) {

    fun encrypt(data: String): String {
        val encrypted = aead.encrypt(data.toByteArray(), null)
        return Base64.encodeToString(encrypted, Base64.DEFAULT).replace("\\r\\n|\\r|\\n".toRegex(), "")
    }

    fun decrypt(data: String): String {
        if (data.isEmpty()) return ""
        val encryptedBytes = Base64.decode(data.replace("\\r\\n|\\r|\\n".toRegex(), ""), Base64.DEFAULT)
        return String(aead.decrypt(encryptedBytes, null), StandardCharsets.UTF_8)
    }

}