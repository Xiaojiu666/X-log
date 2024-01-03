package com.x.log.filter

class LoggerLevelFilter(
    var logLevel: LogLevel?,
    match: FilterResult,
    misMatch: FilterResult,
) : AbstractFilter(match, misMatch) {

    fun filter(methodLevel: LogLevel?): FilterResult? {
        if (methodLevel!!.intLevel >= logLevel!!.intLevel) {
            return FilterResult.NEUTRAL
        }
        return FilterResult.DENY
    }

    override fun filter(): FilterResult? {
        return filter(destLevel)
    }

    companion object {

        fun createFilter(
            level: LogLevel?,
            match: FilterResult = FilterResult.NEUTRAL,
            mismatch: FilterResult = FilterResult.DENY
        ): LoggerLevelFilter {
            val onLevel: LogLevel = level ?: LogLevel.ALL
            return LoggerLevelFilter(onLevel, match, mismatch)
        }
    }


}