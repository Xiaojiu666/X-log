package com.x.log.output

import android.os.Build
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.x.log.HeaderInfo
import com.x.log.service.LogMessage
import com.x.log.utils.CipherManager
import com.x.log.utils.timestampToDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets.UTF_8
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class FileConsoleAdapter(
    override val isLoggable: Boolean,
    private val file: File,
    private val isZip: Boolean = false,
    private val encryption: String? = null,
) :
    ConsoleAdapter {

    init {
        initHeadConfig()
    }

    private fun initHeadConfig() {
        try {
            val headerInfo = HeaderInfo().toString()
            if (isZip) {
                val fileOutputStream = FileOutputStream(file, true)
                val gzipOutputStream = GZIPOutputStream(fileOutputStream)
                val writerByteArray = if (encryption.isNullOrEmpty()) {
                    headerInfo.toByteArray(Charsets.UTF_8)
                } else {
                    CipherManager.getInstance(encryption)
                        .encrypt(headerInfo.toByteArray(Charsets.UTF_8))
                }
                gzipOutputStream.write(writerByteArray)
                gzipOutputStream.close()
            } else {
                val fileWriter = FileWriter(file, true)
                fileWriter.write(headerInfo)
                fileWriter.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun log(logMsg: LogMessage) {
        withContext(Dispatchers.IO) {
            val consoleMsg = "${logMsg.toFileFormat()}\n"
            if (isZip) {
                val fileOutputStream = FileOutputStream(
                    file, true
                )
                val gzipOutputStream = GZIPOutputStream(fileOutputStream)
                val writerByteArray = if (encryption.isNullOrEmpty()) {
                    consoleMsg.toByteArray()
                } else {
                    CipherManager.getInstance(encryption)
                        .encrypt(consoleMsg.toByteArray())
                }
                gzipOutputStream.write(writerByteArray)
                gzipOutputStream.close()
            } else {
                val fileWriter = FileWriter(file, true)
                fileWriter.write(consoleMsg)
                fileWriter.close()
            }
        }
    }
}