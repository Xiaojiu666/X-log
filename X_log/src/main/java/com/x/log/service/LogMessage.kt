package com.x.log.service

import com.x.log.filter.LogLevel
import com.x.log.utils.timestampToDateTime

data class LogMessage(
    val logLevel: LogLevel,
    val tag: String? = null,
    val message: Any,
    val throwable: Throwable? = null,
    val timestamp: Long = System.currentTimeMillis(),
) {
    fun toFileFormat(): String  = "${timestampToDateTime(timestamp)} $tag $logLevel $message  "
}
