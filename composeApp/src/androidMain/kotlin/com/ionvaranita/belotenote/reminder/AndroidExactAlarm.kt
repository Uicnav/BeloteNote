package com.ionvaranita.belotenote.reminder

import android.Manifest
import android.R
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.util.Calendar
import java.util.concurrent.TimeUnit

object AndroidExactAlarm {
    fun canSchedule(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= 31) {
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.canScheduleExactAlarms()
        } else true
    }

    fun request(context: Context) {
        if (Build.VERSION.SDK_INT >= 31) {
            scheduleTestNow(context)
        }
    }
}

fun scheduleWithWorkManager(context: Context, hour: Int = 20, minute: Int = 0) {
    val delay = calculateDelayUntilNext(hour, minute)
    val request =
        PeriodicWorkRequestBuilder<BeloteReminderWorker>(1, TimeUnit.DAYS).setInitialDelay(
                delay,
                TimeUnit.MILLISECONDS
            ).build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "belote_reminder", ExistingPeriodicWorkPolicy.UPDATE, request
        )
}

fun scheduleTestNow(context: Context, secondsFromNow: Long = 10) {
    val request = OneTimeWorkRequestBuilder<BeloteReminderWorker>()
        .setInitialDelay(secondsFromNow, TimeUnit.SECONDS)
        .build()
    WorkManager.getInstance(context).enqueueUniqueWork("belote_test_now", ExistingWorkPolicy.REPLACE, request)
}

fun calculateDelayUntilNext(hour: Int, minute: Int): Long {
    val now = Calendar.getInstance()
    val next = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        if (before(now)) add(Calendar.DAY_OF_YEAR, 1)
    }
    return next.timeInMillis - now.timeInMillis
}

class BeloteReminderWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val title = "Belote time"
        val body = "Deschide Belote Note și joacă o mână"
        showNotification(title, body)
        return Result.success()
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(title: String, body: String) {
        val channelId = "belote_reminder_channel"
        val mgr =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(channelId, "Belote reminders", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "Reminders to play Belote"
        channel.enableLights(true)
        channel.lightColor = Color.RED
        mgr.createNotificationChannel(channel)

        val notification =
            NotificationCompat.Builder(applicationContext, channelId).setContentTitle(title)
                .setContentText(body).setSmallIcon(R.drawable.ic_popup_reminder).setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH).build()

        NotificationManagerCompat.from(applicationContext).notify(1002, notification)
    }
}