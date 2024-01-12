package com.x.log.utils

import android.annotation.SuppressLint
import java.nio.charset.StandardCharsets.UTF_8
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class CipherManager private constructor(private val password: String) {
    companion object {
        private var instance: CipherManager? = null

        fun getInstance(name: String): CipherManager {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = CipherManager(name)
                    }
                }
            }
            return instance!!
        }
    }

    @SuppressLint("GetInstance")
    private val cipher: Cipher = Cipher.getInstance("AES")
    private val secretKeyFactory: SecretKeyFactory =
        SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
    private val keySpec = PBEKeySpec(password.toCharArray(), "salt".toByteArray(), 65536, 256)
    private val secretKey: SecretKey = secretKeyFactory.generateSecret(keySpec)

    fun encrypt(encryptString: ByteArray): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(secretKey.encoded, "AES"))
        return cipher.doFinal(encryptString)
    }

    fun decrypted(decryptedString: ByteArray): ByteArray {
        // 使用密钥初始化Cipher
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(secretKey.encoded, "AES"))
        return cipher.doFinal(decryptedString)
    }
}