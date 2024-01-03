package com.x.log.output

import android.os.Build
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.x.log.HeaderInfo
import com.x.log.service.LogMessage
import com.x.log.utils.timestampToDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets.UTF_8
import java.util.zip.GZIPOutputStream

class FileConsoleAdapter(
    override val isLoggable: Boolean,
    private val file: File,
    private val isZip: Boolean = false,
    private val encryption: String = "",
) :
    ConsoleAdapter {

    init {
        initHeadConfig()
    }

    private fun initHeadConfig() {
        val headerInfo = HeaderInfo().toString()
        if (isZip) {
            val fileOutputStream = FileOutputStream(file, true)
            val gzipOutputStream = GZIPOutputStream(fileOutputStream)
            gzipOutputStream.write(headerInfo.toByteArray())
            gzipOutputStream.close()
        } else {
            val fileWriter = FileWriter(file, true)
            fileWriter.write(headerInfo)
            fileWriter.close()
        }
    }

    override suspend fun log(logMsg: LogMessage) {
        withContext(Dispatchers.IO) {
            val consoleMsg = "${logMsg.toFileFormat()} \n"
            if (isZip) {
                val fileOutputStream = FileOutputStream(file, true)
                val gzipOutputStream = GZIPOutputStream(fileOutputStream)
                val logByte = consoleMsg.toByteArray()
                gzipOutputStream.write(logByte)
                gzipOutputStream.close()
            } else {
                val fileWriter = FileWriter(file, true)
                fileWriter.write(consoleMsg)
                fileWriter.close()
            }
        }
    }
}