package com.x.log

import com.x.log.filter.LogLevel
import com.x.log.service.LogMessage
import com.x.log.service.PrinterConfig
import com.x.log.service.XPrinter


object XLog {
    private var printer: XPrinter = XPrinter(PrinterConfig.Builder().build())

    fun initPrinterConfig(printerConfig: PrinterConfig) {
        printer = XPrinter(printerConfig)
    }

    fun d(message: Any, tag: String? = null, throwable: Throwable? = null) {
        printer.printLog(LogMessage(LogLevel.DEBUG, tag, message, throwable))
    }

    fun e(message: Any, tag: String? = null, throwable: Throwable? = null) {
        printer.printLog(LogMessage(LogLevel.ERROR, tag, message, throwable))
    }

    fun v(message: Any, tag: String? = null, throwable: Throwable? = null) {
        printer.printLog(LogMessage(LogLevel.VERBOSE, tag, message, throwable))
    }

    fun w(message: Any, tag: String? = null, throwable: Throwable? = null) {
        printer.printLog(LogMessage(LogLevel.WARN, tag, message, throwable))
    }

    fun i(message: Any, tag: String? = null, throwable: Throwable? = null) {
        printer.printLog(LogMessage(LogLevel.INFO, tag, message, throwable))
    }
}