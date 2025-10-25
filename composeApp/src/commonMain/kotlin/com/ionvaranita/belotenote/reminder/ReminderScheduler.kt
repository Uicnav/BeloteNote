package com.ionvaranita.belotenote.reminder
internal const val DAILY_SCHEDULED_NOTIFICATION_HOUR= 11
internal const val DAILY_SCHEDULED_NOTIFICATION_MINUTE= 59
interface ReminderScheduler {
    suspend fun requestPermission(): Boolean
    suspend fun scheduleDaily(hour: Int = DAILY_SCHEDULED_NOTIFICATION_HOUR, minute: Int = DAILY_SCHEDULED_NOTIFICATION_MINUTE, title: String, body: String)
    suspend fun scheduleOneTimeAfterMillis(delayMillis: Long, title: String, body: String)
    suspend fun cancelAll()
}