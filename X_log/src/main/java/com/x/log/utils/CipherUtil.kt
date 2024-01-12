package com.x.log.utils

import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.GZIPInputStream


fun decryptAndWriteToFile(inputFilePath: String, outputFilePath: String, password: String) {
    try {
        // 读取加密的文件内容
        val inputStream = GZIPInputStream(FileInputStream(inputFilePath))
        val encryptedBytes = if (password.isEmpty()) {
            inputStream.readBytes()
        } else {
            CipherManager.getInstance(password).decrypted(inputStream.readBytes())
        }
        inputStream.close()
        val outputStream = FileOutputStream(outputFilePath)
        outputStream.write(encryptedBytes)
        outputStream.close()
        println("解密并写入文件成功：$outputFilePath")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun filterControlChars(input: ByteArray): ByteArray {
    val baos = ByteArrayOutputStream()
    input.forEach { byte ->
        if (!isControlChar(byte)) {
            baos.write(byte)
        }
    }
    return baos.toByteArray()
}

fun isControlChar(b: Byte): Boolean {
    return b.toInt() in 0..31 || b.toInt() == 127
}