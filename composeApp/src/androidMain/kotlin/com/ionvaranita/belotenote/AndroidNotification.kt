package com.ionvaranita.belotenote

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat

internal object AndroidNotification {
    const val CHANNEL_ID = "belote_daily_channel"
    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val ch =
                NotificationChannel(CHANNEL_ID, "Belote Daily", NotificationManager.IMPORTANCE_HIGH)
            ch.enableLights(true)
            ch.lightColor = Color.RED
            ch.enableVibration(true)
            nm.createNotificationChannel(ch)
        }
    }
    fun build(context: Context, title: String, body: String, activityClass: Class<*>, requestCode: Int): Notification {
        val intent = Intent(context, activityClass).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pi = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle(title).setContentText(body).setSmallIcon(context.applicationInfo.icon).setContentIntent(pi).setAutoCancel(true).build()
    }
}