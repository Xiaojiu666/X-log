package com.x.log.service

interface LoggerService {


    fun verbose(message: Any)

    fun verbose(tag: String, message: Any)

    fun verbose(tag: String, message: Any, vararg params: String)

    fun debug(message: Any)

    fun debug(tag: String, message: Any)

    fun info(message: Any)

    fun warn(message: Any)

    fun error(message: Any)

    fun error(tag: String, message: Any)
}