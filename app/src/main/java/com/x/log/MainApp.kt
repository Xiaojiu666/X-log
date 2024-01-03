package com.x.log

import android.app.Application
import android.os.Environment
import com.x.log.filter.LevelConsoleFilter
import com.x.log.filter.TimeConsoleFilter
import com.x.log.output.AndroidConsoleAdapter
import com.x.log.output.FileConsoleAdapter
import com.x.log.service.PrinterConfig
import java.io.File

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initLog()
    }

    private fun initLog() {
        val printerConfig = PrinterConfig.Builder().apply {
            addConsoleAdapter(
                AndroidConsoleAdapter(
                    true,
                    TimeConsoleFilter() + LevelConsoleFilter()
                )
            )
            val dir = baseContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val file = File(dir, "text.txt")
            addConsoleAdapter(
                FileConsoleAdapter(true, file)
            )
        }.build()
        XLog.initPrinterConfig(printerConfig)
    }
}