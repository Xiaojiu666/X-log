package com.x.log.service

import com.x.log.layout.StackTraceTagStrategy
import com.x.log.layout.TagStrategy
import com.x.log.output.ConsoleAdapter

class PrinterConfig private constructor(
    val adapters: Array<ConsoleAdapter>,
    val tagStrategy: TagStrategy,
) {

    class Builder {
        private val adapters: MutableMap<String, ConsoleAdapter> = mutableMapOf()
        private var tagStrategy: TagStrategy = StackTraceTagStrategy()

        fun addTag(tagStrategy: TagStrategy) = apply {
            this.tagStrategy = tagStrategy
        }

        fun addConsoleAdapter(logAdapter: ConsoleAdapter) = apply {
            val adapterKey = logAdapter::class.java.simpleName
            if (adapters.containsKey(adapterKey).not()) {
                adapters[adapterKey] = logAdapter
            }
        }

        fun build(): PrinterConfig {
            return PrinterConfig(
                adapters.values.toTypedArray(),
                tagStrategy,
            )
        }
    }
}