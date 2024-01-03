package com.x.log.output

import android.util.Log
import com.x.log.filter.ConsoleFilter
import com.x.log.service.LogMessage


class AndroidConsoleAdapter(
    override val isLoggable: Boolean,
    private val consoleFilter: ConsoleFilter
) : ConsoleAdapter {

    override suspend fun log(logMsg: LogMessage) {
        if (consoleFilter.filterResult) {
            Log.println(logMsg.logLevel.intLevel, logMsg.tag, logMsg.message.toString())
        }
    }
}