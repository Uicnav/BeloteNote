package com.ionvaranita.belotenote.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ionvaranita.belotenote.AndroidNotification

class AndroidAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        AndroidNotification.ensureChannel(context)
        val cls = ReminderSchedulerAndroidHolder.activityClass()
        val n = AndroidNotification.build(context, "E timpul pentru Belote", "Deschide Belote Note", cls, 1001)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        nm.notify(1001, n)
        val time = TimeStoreAndroid.readSync(context) ?: return
        if (AndroidExactAlarm.canSchedule(context)) {
            AndroidReminderScheduler.scheduleExact(context, time.hour, time.minute)
        } else {
            AndroidReminderScheduler.scheduleInexact(context, time.hour, time.minute)
        }
    }
}