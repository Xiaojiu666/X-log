package com.x.log.style

import com.x.log.layout.LogFormat

open class AbsLoggerFormat : LogFormat {

    override fun format(message: Any): String {
        return message.toString()
    }
}