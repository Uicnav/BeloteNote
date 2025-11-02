package com.ionvaranita.belotenote.reminder

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ionvaranita.belotenote.MainActivity
import com.ionvaranita.belotenote.reminder.BeloteNotificationReceiver.Companion.CHANNEL_ID
import com.ionvaranita.belotenote.reminder.BeloteNotificationReceiver.Companion.NOTIF_ID
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BeloteNotificationReceiver : BroadcastReceiver() {
    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        GlobalScope.launch(Dispatchers.Default) {
            notify(context)
        }
    }

    companion object {
        const val CHANNEL_ID = "belote_reminders"
        const val NOTIF_ID = 1001
    }
}

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
@RequiresApi(Build.VERSION_CODES.O)
internal suspend fun notify(context: Context) {
    if (isAppInForeground(context)) return // don't show if app is visible

    val reminder = localizedReminderTexts()
    val title = reminder.first
    val body = reminder.second
    ensureChannel(context)
    val mainIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(
        context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle(title)
        .setContentText(body)
        .setSmallIcon(android.R.drawable.ic_popup_reminder)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .build()

    NotificationManagerCompat.from(context).notify(NOTIF_ID, notification)
}

private fun isAppInForeground(context: Context): Boolean {
    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
    val processes = am.runningAppProcesses ?: return false
    val packageName = context.packageName
    for (process in processes) {
        if (process.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
            process.processName == packageName
        ) {
            return true
        }
    }
    return false
}


@RequiresApi(Build.VERSION_CODES.O)
private fun ensureChannel(context: Context) {
    val mgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channel =
        NotificationChannel(CHANNEL_ID, "Belote Reminders", NotificationManager.IMPORTANCE_HIGH)
    channel.description = "Reminders to play Belote"
    channel.enableLights(true)
    channel.lightColor = Color.RED
    channel.enableVibration(true)
    mgr.createNotificationChannel(channel)
}