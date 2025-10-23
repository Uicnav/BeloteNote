package com.ionvaranita.belotenote.reminder

expect object AndroidReminderScheduler {
    suspend fun requestPermission()
    suspend fun hasPermission(): Boolean
    suspend fun scheduleDaily(time: ReminderTime)
    suspend fun cancel()
    suspend fun loadSavedTime(): ReminderTime?
}