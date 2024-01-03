package com.x.log

import com.x.log.filter.LogFilter
import com.x.log.service.LoggerService
import java.lang.reflect.Proxy

class Logger() {


    class Builder(var loggerServiceImpl: LoggerService) {
        private var loggerFilter: LogFilter? = null

        fun setLoggerFilter(loggerFilter: LogFilter): Builder {
            this.loggerFilter = loggerFilter
            return this
        }

        fun build(): LoggerService {
            val loggerService = LoggerService::class.java
            return Proxy.newProxyInstance(
                loggerService.classLoader, arrayOf<Class<*>>(loggerService)
            ) { proxy, method, args ->
//                val analysisAnnotation = analysisAnnotation(method)
//                if (loggerFilter!!.filter(analysisAnnotation) == FilterResult.NEUTRAL) {
//                    args.forEach {
//                        Log.d(TAG, it.javaClass.name)
//                    }
//                    method.invoke(loggerServiceImpl, *args)
//                }
            } as LoggerService
        }

//        private fun analysisAnnotation(method: Method): LogLevel {
//            val annotations = method.annotations
//            if (annotations.isNotEmpty()) {
//                annotations.forEach { annotation ->
//                    if (annotation is FilterMapping) {
//                        return annotation.level
//                    }
//                }
//            }
//            return LogLevel.ALL
//        }
    }
}