package com.ionvaranita.belotenote.reminder

import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSDateComponents
import platform.UserNotifications.*
import kotlin.coroutines.resume

class IosReminderSchedulerImpl : ReminderScheduler {

    override suspend fun requestPermission(): Boolean = suspendCancellableCoroutine { cont ->
        UNUserNotificationCenter.currentNotificationCenter().requestAuthorizationWithOptions(
            UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge
        ) { granted, _ -> cont.resume(granted) }
    }

    override suspend fun scheduleDaily(hour: Int, minute: Int) {
        val reminder = localizedReminderTexts()

        val content = UNMutableNotificationContent()
        content.setTitle(reminder.first)
        content.setBody(reminder.second)
        content.setSound(UNNotificationSound.defaultSound())

        val comps = NSDateComponents().apply {
            setHour(hour.toLong())
            setMinute(minute.toLong())
        }

        val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(comps, true)
        val request = UNNotificationRequest.requestWithIdentifier(
            "belote_daily_${hour}_${minute}",
            content,
            trigger
        )

        UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(request, null)
    }

    override suspend fun cancelAll() {
        UNUserNotificationCenter.currentNotificationCenter()
            .removeAllPendingNotificationRequests()
        UNUserNotificationCenter.currentNotificationCenter()
            .removeAllDeliveredNotifications()
    }
}
