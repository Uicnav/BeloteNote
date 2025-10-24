package com.ionvaranita.belotenote.reminder

import android.app.PendingIntent
import android.content.Context
import android.content.Intent

fun pendingBroadcast(context: Context, requestCode: Int, intent: Intent): PendingIntent {
    val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    return PendingIntent.getBroadcast(context, requestCode, intent, flags)
}