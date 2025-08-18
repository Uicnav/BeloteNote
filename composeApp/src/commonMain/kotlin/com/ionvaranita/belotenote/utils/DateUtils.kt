package com.ionvaranita.belotenote.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun getCurrentTime(): Long {
    return Clock.System.now().epochSeconds
}