package com.x.log.service

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

open class XPrinter(printerConfig: PrinterConfig) {
    private val scope = CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val consoleAdapter = printerConfig.adapters
    private val tagStrategy = printerConfig.tagStrategy
    private val sharedFlow = MutableSharedFlow<LogMessage>()

    init {
        scope.launch {
            sharedFlow.collect { logMsg ->
                consoleAdapter.filter {
                    it.isLoggable
                }.onEach {
                    it.log(logMsg)
                }
            }
        }
    }

    internal fun printLog(logMessage: LogMessage) {
        val logMsg = logMessage.copy(tag = tagStrategy.createTag(logMessage.tag))
        scope.launch {
            sharedFlow.emit(logMsg)
        }
    }

    class CloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
        override val coroutineContext: CoroutineContext = context

        override fun close() {
            coroutineContext.cancel()
        }
    }
}