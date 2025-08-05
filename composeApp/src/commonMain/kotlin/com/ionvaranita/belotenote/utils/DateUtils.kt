package com.ionvaranita.belotenote.utils

import kotlinx.datetime.Clock


fun getCurrentTime(): Long {
    return Clock.System.now().epochSeconds
}