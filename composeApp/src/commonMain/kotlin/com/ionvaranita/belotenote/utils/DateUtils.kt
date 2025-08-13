package com.ionvaranita.belotenote.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun getCurrentTime(): Long {
    return Clock.System.now().epochSeconds
}

fun Long.getFormattedDate(): String {
    val instant = Instant.fromEpochSeconds(this)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dateTime.dayOfMonth.toString().padStart(2, '0')}/" +
            "${dateTime.monthNumber.toString().padStart(2, '0')}/" +
            "${dateTime.year} ${dateTime.hour.toString().padStart(2, '0')}:${dateTime.minute.toString().padStart(2, '0')}"
}