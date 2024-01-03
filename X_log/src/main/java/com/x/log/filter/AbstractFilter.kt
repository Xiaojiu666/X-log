package com.x.log.filter

open class AbstractFilter(
    private var match: FilterResult = FilterResult.NEUTRAL,
    private var misMatch: FilterResult = FilterResult.DENY
) : LogFilter {

    var destLevel: LogLevel = LogLevel.ALL

    override fun getOnMatch(): FilterResult {
        return match
    }

    override fun getOnMismatch(): FilterResult {
        return misMatch
    }


    override fun filter(): FilterResult? {
        return FilterResult.NEUTRAL
    }

    override fun setLevel(logLevel: LogLevel) {
        destLevel = logLevel
    }


}