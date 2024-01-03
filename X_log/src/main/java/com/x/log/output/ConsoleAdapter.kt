package com.x.log.output

import com.x.log.service.LogMessage


interface ConsoleAdapter {
    val isLoggable: Boolean

    suspend fun log(logMsg: LogMessage)
}

