package com.ionvaranita.belotenote.reminder

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat
import com.ionvaranita.belotenote.store.AndroidReminderStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AndroidReminderSchedulerImpl(private val context: Context) : ReminderScheduler {
    private val store = AndroidReminderStore(context)
    override suspend fun requestPermission(): Boolean = withContext(Dispatchers.Main) {
        if (Build.VERSION.SDK_INT >= 33) {
            val nm = NotificationManagerCompat.from(context)
            nm.areNotificationsEnabled()
        } else true
    }

    @RequiresPermission(value = Manifest.permission.SCHEDULE_EXACT_ALARM, conditional = true)
    override suspend fun scheduleDaily(hour: Int , minute: Int) {
        withContext(Dispatchers.IO) {
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, BeloteNotificationReceiver::class.java).apply {
                action = "com.belote.shared.ACTION_SEND_BELOTE_NOTIFICATION"
            }
            val pedingBroadcase = pendingBroadcast(context, 2001, intent)
            val cal = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                if (before(Calendar.getInstance())) add(Calendar.DAY_OF_YEAR, 1)
            }
            val trigger = cal.timeInMillis
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pedingBroadcase)
        }
    }

    override suspend fun cancelAll() {
        withContext(Dispatchers.IO) {
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, BeloteNotificationReceiver::class.java).apply {
                action = "com.belote.shared.ACTION_SEND_BELOTE_NOTIFICATION"
            }
            val piDaily = pendingBroadcast(context, 2001, intent)
            val piOne = pendingBroadcast(context, 2002, intent)
            am.cancel(piDaily)
            am.cancel(piOne)
            store.clear()
        }
    }

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun rescheduleDaily(hour: Int, minute: Int, title: String, body: String) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, BeloteNotificationReceiver::class.java).apply {
            action = "com.belote.shared.ACTION_SEND_BELOTE_NOTIFICATION"
            putExtra("title", title)
            putExtra("body", body)
        }
        val pi = pendingBroadcast(context, 2001, intent)
        val cal = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(Calendar.getInstance())) add(Calendar.DAY_OF_YEAR, 1)
        }
        val trigger = cal.timeInMillis
        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pi)
    }
}