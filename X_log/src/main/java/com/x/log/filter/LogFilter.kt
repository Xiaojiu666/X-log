package com.x.log.filter

interface LogFilter {

    fun getOnMatch(): FilterResult

    fun getOnMismatch(): FilterResult

    fun filter(): FilterResult?

    fun setLevel(logLevel: LogLevel)


}