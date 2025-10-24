package com.ionvaranita.belotenote.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ionvaranita.belotenote.store.AndroidReminderStore

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val store = AndroidReminderStore(context)
        val data = store.loadDaily()
        if (data != null) {
            AndroidReminderSchedulerImpl(context).rescheduleDaily(data.hour, data.minute, data.title, data.body)
        }
    }
}