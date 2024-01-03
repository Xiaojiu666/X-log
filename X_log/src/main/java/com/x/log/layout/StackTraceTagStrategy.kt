package com.x.log.layout

class StackTraceTagStrategy: TagStrategy {
    override fun createTag(tag: String?): String {
        return tag?: tagFromStackTrace(Throwable().stackTrace)
    }
    private fun tagFromStackTrace(stacktrace: Array<StackTraceElement>): String {
        return if (stacktrace.size < CALL_STACK_INDEX) {
           "NULL"
        } else {
            tagFromStacktraceElement(stacktrace)
        }
    }

    private fun tagFromStacktraceElement(stacktrace: Array<StackTraceElement>): String {
        val fullClassName = stacktrace[CALL_STACK_INDEX].className
        val tag = fullClassName.substring(fullClassName.lastIndexOf('.') + 1)
        return if (tag.length <= MAX_TAG_LENGTH) {
            tag
        } else {
            tag.substring(0, MAX_TAG_LENGTH)
        }
    }

    private companion object {
        /**
         * This index is based on current implementation of MBLoggerKit.
         */
        private const val CALL_STACK_INDEX = 4

        /**
         * Maximum length of TAGs, required on SDK_INT < 24.
         */
        private const val MAX_TAG_LENGTH = 13
    }
}