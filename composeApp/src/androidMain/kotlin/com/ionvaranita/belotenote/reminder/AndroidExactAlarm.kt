package com.ionvaranita.belotenote.reminder

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings

object AndroidExactAlarm {
    fun canSchedule(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= 31) {
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.canScheduleExactAlarms()
        } else true
    }
    fun request(context: Context) {
        if (Build.VERSION.SDK_INT >= 31) {
            val i = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }
}