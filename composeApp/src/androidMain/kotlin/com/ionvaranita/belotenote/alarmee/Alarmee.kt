package com.ionvaranita.belotenote.alarmee

import android.app.NotificationManager
import androidx.compose.ui.graphics.Color
import com.tweener.alarmee.channel.AlarmeeNotificationChannel
import com.tweener.alarmee.configuration.AlarmeeAndroidPlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration

actual fun createAlarmeePlatformConfiguration(): AlarmeePlatformConfiguration =
    AlarmeeAndroidPlatformConfiguration(
        notificationIconResId = com.tweener.alarmee.android.R.drawable.ic_notification,
        notificationIconColor = Color.Red, // Defaults to Color.Transparent is not specified
        useExactScheduling = true, // Enable exact alarm scheduling for more precise timing (Android 12+, requires SCHEDULE_EXACT_ALARM permission)
        notificationChannels = listOf(
            AlarmeeNotificationChannel(
                id = "dailyNewsChannelId",
                name = "Daily news notifications",
                importance = NotificationManager.IMPORTANCE_HIGH,
                soundFilename = "notifications_sound",
            ),
            AlarmeeNotificationChannel(
                id = "breakingNewsChannelId",
                name = "Breaking news notifications",
                importance = NotificationManager.IMPORTANCE_LOW,
            ),
            // List all the notification channels you need here
        )
    )