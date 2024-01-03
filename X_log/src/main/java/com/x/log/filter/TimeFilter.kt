package com.x.log.filter

import java.time.*
import java.time.format.DateTimeFormatter


class TimeFilter internal constructor(
    var startLocalTime: LocalTime,
    var endLocalTime: LocalTime,
    var timeZone: ZoneId,
    var match: FilterResult,
    var misMatch: FilterResult,
    var now: LocalDate
) : AbstractFilter(match, misMatch) {


    val HOUR_MS: Long = 3600000

    val DAY_MS = HOUR_MS * 24


    var duration: Long = 0

    /**
     * Starting offset from midnight in milliseconds.
     */
    private var start: Long =
        ZonedDateTime.of(now, startLocalTime, timeZone).withEarlierOffsetAtOverlap().toInstant()
            .toEpochMilli()

    /**
     * Ending offset from midnight in milliseconds.
     */
    private var end: Long = 0

    init {
        var startMillis =
            ZonedDateTime.of(now, startLocalTime, timeZone).withEarlierOffsetAtOverlap().toInstant()
                .toEpochMilli()
        var endMillis: Long =
            ZonedDateTime.of(now, endLocalTime, timeZone).withEarlierOffsetAtOverlap().toInstant()
                .toEpochMilli()
        if (endLocalTime.isBefore(startLocalTime)) {
            // End time must be tomorrow.
            endMillis += DAY_MS
        }
        duration = if (startLocalTime.isBefore(endLocalTime))
            Duration.between(startLocalTime, endLocalTime)
                .toMillis()
        else Duration.between(startLocalTime, endLocalTime).plusHours(24).toMillis()
        val difference = endMillis - startMillis - duration
        if (difference != 0L) {
            // Handle switch from standard time to daylight time and daylight time to standard time.
            endMillis -= difference
        }
        end = endMillis
    }


    constructor(
        start: LocalTime, end: LocalTime, timeZone: ZoneId, onMatch: FilterResult,
        onMismatch: FilterResult
    ) : this(start, end, timeZone, onMatch, onMismatch, LocalDate.now(timeZone)) {

    }

    @Synchronized
    private fun adjustTimes(currentTimeMillis: Long) {
        if (currentTimeMillis <= end) {
            return
        }
        val date = Instant.ofEpochMilli(currentTimeMillis).atZone(timeZone).toLocalDate()
        start = ZonedDateTime.of(date, startLocalTime, timeZone).withEarlierOffsetAtOverlap()
            .toInstant()
            .toEpochMilli()
        var endMillis =
            ZonedDateTime.of(date, endLocalTime, timeZone).withEarlierOffsetAtOverlap().toInstant()
                .toEpochMilli()
        if (endLocalTime!!.isBefore(startLocalTime)) {
            // End time must be tomorrow.
            endMillis += DAY_MS
        }
        val difference = endMillis - start - duration
        if (difference != 0L) {
            // Handle switch from standard time to daylight time and daylight time to standard time.
            endMillis -= difference
        }
        end = endMillis
    }

    private fun filter(currentTimeMillis: Long): FilterResult {
        if (currentTimeMillis > end) {
            adjustTimes(currentTimeMillis)
        }
        return if (currentTimeMillis in start..end) getOnMatch() else getOnMismatch()
    }

    override fun filter(): FilterResult {
        return filter(System.currentTimeMillis())
    }


    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("start=").append(start)
        sb.append(", end=").append(end)
        sb.append(", timezone=").append(timeZone.toString())
        return sb.toString()
    }





    companion object{
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        fun createFilter(
            start: String?,
            end: String?,
            tz: String?,
            match: FilterResult?,
            mismatch: FilterResult?
        ): TimeFilter {
            val startTime: LocalTime = parseTimestamp(start, LocalTime.MIN)
            val endTime: LocalTime = parseTimestamp(end, LocalTime.MAX)
            val timeZone = if (tz == null) ZoneId.systemDefault() else ZoneId.of(tz)
            val onMatch: FilterResult = match ?: FilterResult.NEUTRAL
            val onMismatch: FilterResult = mismatch ?: FilterResult.DENY
            return TimeFilter(startTime, endTime, timeZone, onMatch, onMismatch)
        }

        private fun parseTimestamp(timestamp: String?, defaultValue: LocalTime): LocalTime {
            return if (timestamp == null) {
                defaultValue
            } else try {
                LocalTime.parse(timestamp, FORMATTER)
            } catch (e: Exception) {
                defaultValue
            }
        }
    }

}