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

class BeloteNotificationReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        notify(context)
    }

    companion object {
        const val CHANNEL_ID = "belote_reminders"
        const val NOTIF_ID = 1001
    }
}

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
@RequiresApi(Build.VERSION_CODES.O)
internal fun notify(context: Context) {
    val title = context.getString(R.string.ok) ?: "Belote"
    val body = context.getString(R.string.no) ?: "Belote"
    ensureChannel(context)
    val mainIntent = Intent(context, MainActivity::class.java)
    mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    val pendingIntent = PendingIntent.getActivity(
        context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val notification =
        NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle(title).setContentText(body)
            .setSmallIcon(R.drawable.ic_popup_reminder).setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent).build()
    NotificationManagerCompat.from(context).notify(NOTIF_ID, notification)
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