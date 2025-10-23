package com.ionvaranita.belotenote.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import com.ionvaranita.belotenote.AndroidNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual object AndroidReminderScheduler {
    fun init(context: Context, mainActivityClass: Class<*>) { ReminderSchedulerAndroidHolder.init(context, mainActivityClass) }
    actual suspend fun scheduleDaily(time: ReminderTime) {
        withContext(Dispatchers.Default) {
            TimeStoreAndroid.writeSync(ReminderSchedulerAndroidHolder.ctx(), time)
            AndroidNotification.ensureChannel(ReminderSchedulerAndroidHolder.ctx())
            if (AndroidExactAlarm.canSchedule(ReminderSchedulerAndroidHolder.ctx())) {
                scheduleExact(ReminderSchedulerAndroidHolder.ctx(), time.hour, time.minute)
            } else {
                scheduleInexact(ReminderSchedulerAndroidHolder.ctx(), time.hour, time.minute)
            }
        }
    }
    actual suspend fun cancel() {
        withContext(Dispatchers.Default) {
            val ctx = ReminderSchedulerAndroidHolder.ctx()
            val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pi = PendingIntent.getBroadcast(ctx, 2001, Intent(ctx, AndroidAlarmReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            am.cancel(pi)
            TimeStoreAndroid.clearSync(ctx)
        }
    }
    actual suspend fun loadSavedTime(): ReminderTime? = withContext(Dispatchers.Default) { TimeStoreAndroid.readSync(ReminderSchedulerAndroidHolder.ctx()) }
    internal fun scheduleExact(context: Context, hour: Int, minute: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AndroidAlarmReceiver::class.java)
        val pedingIntent = PendingIntent.getBroadcast(context, 2001, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val cal = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        val now = System.currentTimeMillis()
        var triggerAt = cal.timeInMillis
        if (triggerAt <= now) triggerAt += 24L * 60L * 60L * 1000L
        if (Build.VERSION.SDK_INT >= 23) alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pedingIntent) else alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAt, pedingIntent)
    }
    internal fun scheduleInexact(context: Context, hour: Int, minute: Int) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AndroidAlarmReceiver::class.java)
        val pi = PendingIntent.getBroadcast(context, 2001, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val cal = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        val now = System.currentTimeMillis()
        var firstAt = cal.timeInMillis
        if (firstAt <= now) firstAt += 24L * 60L * 60L * 1000L
        if (Build.VERSION.SDK_INT >= 19) {
            am.setWindow(AlarmManager.RTC_WAKEUP, firstAt, 15L * 60L * 1000L, pi)
        } else {
            am.set(AlarmManager.RTC_WAKEUP, firstAt, pi)
        }
    }

    actual suspend fun requestPermission() {
    }

    actual suspend fun hasPermission(): Boolean {
        TODO("Not yet implemented")
    }
}