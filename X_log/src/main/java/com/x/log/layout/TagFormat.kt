package com.x.log.layout

open class TagFormat : LogFormat {


    override fun format(message: Any): String {
        return message.toString()
    }
}