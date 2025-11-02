package com.ionvaranita.belotenote.reminder

import belotenote.composeapp.generated.resources.Res
import belotenote.composeapp.generated.resources.reminder_body
import belotenote.composeapp.generated.resources.reminder_title
import org.jetbrains.compose.resources.getString
internal const val DAILY_SCHEDULED_NOTIFICATION_HOUR= 8
internal const val DAILY_SCHEDULED_NOTIFICATION_MINUTE= 15
interface ReminderScheduler {
    suspend fun requestPermission(): Boolean
    suspend fun scheduleDaily(hour: Int = DAILY_SCHEDULED_NOTIFICATION_HOUR, minute: Int = DAILY_SCHEDULED_NOTIFICATION_MINUTE)
    suspend fun cancelAll()
}


suspend fun localizedReminderTexts(): Pair<String, String> {
    val title = getString(Res.string.reminder_title)
    val body = getString(Res.string.reminder_body)
    return title to body
}