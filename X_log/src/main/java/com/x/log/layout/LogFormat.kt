package com.x.log.layout

interface LogFormat {

    fun format(message: Any): String

}