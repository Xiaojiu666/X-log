package com.x.log.filter

open class ConsoleFilter {

    var filterResult: Boolean = false


    operator fun plus(consoleFilter: ConsoleFilter): ConsoleFilter {
        val consoleFilter1 = ConsoleFilter()
        consoleFilter1.filterResult = consoleFilter.filterResult && filterResult
        return consoleFilter1
    }
}