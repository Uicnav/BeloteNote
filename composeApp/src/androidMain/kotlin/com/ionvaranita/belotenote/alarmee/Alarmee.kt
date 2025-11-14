package com.ionvaranita.belotenote.alarmee

import android.app.NotificationManager
import com.ionvaranita.belotenote.R
import com.ionvaranita.belotenote.breakingNewsChannelId
import com.ionvaranita.belotenote.dailyNewsChannelId
import com.tweener.alarmee.channel.AlarmeeNotificationChannel
import com.tweener.alarmee.configuration.AlarmeeAndroidPlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration


actual fun createAlarmeePlatformConfiguration(): AlarmeePlatformConfiguration =
    AlarmeeAndroidPlatformConfiguration(
        notificationIconResId = R.drawable.ic_notification_reminder,
        useExactScheduling = true, // Enable exact alarm scheduling for more precise timing (Android 12+, requires SCHEDULE_EXACT_ALARM permission)
        notificationChannels = listOf(
            AlarmeeNotificationChannel(
                id = dailyNewsChannelId,
                name = "Daily news notifications",
                importance = NotificationManager.IMPORTANCE_HIGH,
                soundFilename = "notifications_sound",
            ),
            AlarmeeNotificationChannel(
                id = breakingNewsChannelId,
                name = "Breaking news notifications",
                soundFilename = "notifications_sound",
                importance = NotificationManager.IMPORTANCE_HIGH,
            ),
            // List all the notification channels you need here
        )
    )