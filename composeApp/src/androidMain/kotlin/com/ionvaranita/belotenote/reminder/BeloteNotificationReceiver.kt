package com.ionvaranita.belotenote.reminder

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class BeloteNotificationReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Belote"
        val body = intent.getStringExtra("body") ?: "Hai la Belote"
        ensureChannel(context)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_popup_reminder)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        NotificationManagerCompat.from(context).notify(NOTIF_ID, notification)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun ensureChannel(context: Context) {
        val mgr = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(CHANNEL_ID, "Belote Reminders", NotificationManager.IMPORTANCE_HIGH)
        channel.description = "Reminders to play Belote"
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        mgr.createNotificationChannel(channel)
    }
    companion object {
        const val CHANNEL_ID = "belote_reminders"
        const val NOTIF_ID = 1001
    }
}