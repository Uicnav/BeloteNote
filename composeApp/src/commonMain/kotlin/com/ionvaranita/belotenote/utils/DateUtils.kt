package com.ionvaranita.belotenote.utils

import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
fun getCurrentTime(): Long {
    return Clock.System.now().epochSeconds
}