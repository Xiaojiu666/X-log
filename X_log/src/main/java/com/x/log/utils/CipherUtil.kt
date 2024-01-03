package com.x.log.utils

import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.GZIPInputStream


fun decryptAndWriteToFile(inputFilePath: String, outputFilePath: String, password: String) {
    try {
        // 读取加密的文件内容
        val inputStream = GZIPInputStream(FileInputStream(inputFilePath))
        val encryptedBytes = inputStream.readBytes()
        inputStream.close()
        val outputStream = FileOutputStream(outputFilePath)
        outputStream.write(encryptedBytes)
        outputStream.close()
        println("解密并写入文件成功：$outputFilePath")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
