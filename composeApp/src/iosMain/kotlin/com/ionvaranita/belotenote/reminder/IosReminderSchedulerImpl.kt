package com.ionvaranita.belotenote.reminder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter
import platform.Foundation.NSDateComponents
import kotlin.coroutines.resume
import kotlin.math.max

class IosReminderSchedulerImpl : ReminderScheduler {
    override suspend fun requestPermission(): Boolean = suspendCancellableCoroutine { cont ->
        UNUserNotificationCenter.currentNotificationCenter().requestAuthorizationWithOptions(
            options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge
        ) { g, _ -> cont.resume(g) }
    }

    override suspend fun scheduleDaily(hour: Int, minute: Int, title: String, body: String) {
        withContext(Dispatchers.Main.immediate) {
            val content = UNMutableNotificationContent()
            content.setTitle(title)
            content.setBody(body)
            content.setSound(UNNotificationSound.defaultSound())
            val comps = NSDateComponents()
            comps.setHour(hour.toLong())
            comps.setMinute(minute.toLong())
            val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(comps, true)
            val request = UNNotificationRequest.requestWithIdentifier("belote_daily_${hour}_${minute}", content, trigger)
            UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(request, null)
        }
    }

    override suspend fun scheduleOneTimeAfterMillis(delayMillis: Long, title: String, body: String) {
        withContext(Dispatchers.Main.immediate) {
            val content = UNMutableNotificationContent()
            content.setTitle(title)
            content.setBody(body)
            content.setSound(UNNotificationSound.defaultSound())
            val seconds = max(1, (delayMillis / 1000).toInt())
            val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(seconds.toDouble(), false)
            val request = UNNotificationRequest.requestWithIdentifier("belote_one_${seconds}", content, trigger)
            UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(request, null)
        }
    }

    override suspend fun cancelAll() {
        withContext(Dispatchers.Main.immediate) {
            UNUserNotificationCenter.currentNotificationCenter().removeAllPendingNotificationRequests()
            UNUserNotificationCenter.currentNotificationCenter().removeAllDeliveredNotifications()
        }
    }
}
