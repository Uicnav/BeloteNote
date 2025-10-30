package com.ionvaranita.belotenote.reminder

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
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

    @RequiresPermission(value = Manifest.permission.SCHEDULE_EXACT_ALARM, conditional = true)
    override suspend fun scheduleDaily(hour: Int, minute: Int) {
        val wm = WorkManager.getInstance(context)
        val workInfos = WorkManager.getInstance(context)
            .getWorkInfosForUniqueWork("belote_test_now")
            .get()
        val alreadyScheduled = workInfos.any {
            it.state == WorkInfo.State.ENQUEUED || it.state == WorkInfo.State.RUNNING
        }
        if (!alreadyScheduled) {
            Log.d(TAG, "Schedule is not scheduled, schedule again!")
            val now = Calendar.getInstance()
            val target = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                if (before(now)) add(Calendar.DAY_OF_YEAR, 1)
            }
            val delay = target.timeInMillis - now.timeInMillis
            val request = OneTimeWorkRequestBuilder<BeloteReminderWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build()
            wm.enqueueUniqueWork("belote_test_now", ExistingWorkPolicy.KEEP, request)
        } else {
            Log.d(TAG, "Schedule already scheduled, no need to schedule again")
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
        }
    }

}