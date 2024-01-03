package com.x.log.filter

enum class LogLevel(var intLevel: Int) {


    VERBOSE(2),
    DEBUG(3),
    INFO(4),
    WARN(5),
    ERROR(6),

    /**
     * All events should be logged.
     */
    ALL(-1);

//    private val LEVELSET: EnumSet<LoggerLevel> = EnumSet.allOf(LoggerLevel::class.java)


//    open fun getStandardLevel(intLevel: Int): LoggerLevel? {
//        var level: LoggerLevel? = LoggerLevel.OFF
//        for (lvl in LEVELSET) {
//            if (lvl.intLevel > intLevel) {
//                break
//            }
//            level = lvl
//        }
//        return level
//    }
}