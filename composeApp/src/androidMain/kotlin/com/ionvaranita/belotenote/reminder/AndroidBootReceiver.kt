package com.ionvaranita.belotenote.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AndroidBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val t = TimeStoreAndroid.readSync(context) ?: return
        if (AndroidExactAlarm.canSchedule(context)) {
            AndroidReminderScheduler.scheduleExact(context, t.hour, t.minute)
        } else {
            AndroidReminderScheduler.scheduleInexact(context, t.hour, t.minute)
        }
    }
}