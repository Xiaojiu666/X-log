package com.x.log.filter

enum class FilterResult {
    /**
     * The event will be processed without further filtering based on the log Level.
     */
    ACCEPT,

    /**
     * No decision could be made, further filtering should occur.
     */
    NEUTRAL,

    /**
     * The event should not be processed.
     */
    DENY;

}