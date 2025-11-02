package com.ionvaranita.belotenote.reminder

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.concurrent.TimeUnit


class AndroidReminderSchedulerImpl(private val context: Context) : ReminderScheduler {
    private val TAG = this::class.simpleName
    override suspend fun requestPermission(): Boolean = withContext(Dispatchers.Main) {
        if (Build.VERSION.SDK_INT >= 33) {
            val nm = NotificationManagerCompat.from(context)
            nm.areNotificationsEnabled()
        } else true
    }

    private val timeTreshold = 10

    override suspend fun scheduleDaily(hour: Int, minute: Int) {
        val context = AndroidContextHolder.appContext
        val wm = WorkManager.getInstance(context)
            val now = Calendar.getInstance()
            val target = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                val deltaTime = timeInMillis - (now.timeInMillis + timeTreshold)
                Log.d(TAG, "timeInMillis - now.timeInMillis = ${deltaTime}")
                if (deltaTime <= 0) add(Calendar.DAY_OF_YEAR, 1)
            }
            val delay = target.timeInMillis - now.timeInMillis
            val request = OneTimeWorkRequestBuilder<BeloteReminderWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build()
            wm.enqueueUniqueWork("belote_test_now", ExistingWorkPolicy.REPLACE, request)
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
        }
    }

}