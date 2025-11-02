package com.ionvaranita.belotenote.reminder

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.util.Calendar

class BeloteReminderWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        notify(applicationContext)
        ReminderSchedulerFactory.create().scheduleDaily()
        return Result.success()
    }

}