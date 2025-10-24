package com.ionvaranita.belotenote.reminder
private const val DAILY_SCHEDULED_NOTIFICATION_HOUR= 17
private const val DAILY_SCHEDULED_NOTIFICATION_MINUTE_= 37
interface ReminderScheduler {
    suspend fun requestPermission(): Boolean
    suspend fun scheduleDaily(hour: Int = DAILY_SCHEDULED_NOTIFICATION_HOUR, minute: Int = DAILY_SCHEDULED_NOTIFICATION_MINUTE_, title: String, body: String)
    suspend fun scheduleOneTimeAfterMillis(delayMillis: Long, title: String, body: String)
    suspend fun cancelAll()
}