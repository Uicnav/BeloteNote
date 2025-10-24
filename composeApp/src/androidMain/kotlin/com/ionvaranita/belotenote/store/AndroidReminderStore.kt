package com.ionvaranita.belotenote.store

import android.content.Context
import androidx.core.content.edit

data class DailyData(val hour: Int, val minute: Int, val title: String, val body: String)

class AndroidReminderStore(private val context: Context) {
    private val prefs = context.getSharedPreferences("belote_reminder_store", Context.MODE_PRIVATE)
    fun saveDaily(hour: Int, minute: Int, title: String, body: String) {
        prefs.edit {
            putInt("hour", hour)
                .putInt("minute", minute)
                .putString("title", title)
                .putString("body", body)
        }
    }
    fun loadDaily(): DailyData? {
        if (!prefs.contains("hour")) return null
        val hour = prefs.getInt("hour", 20)
        val minute = prefs.getInt("minute", 0)
        val title = prefs.getString("title", "Belote") ?: "Belote"
        val body = prefs.getString("body", "Hai la Belote") ?: "Hai la Belote"
        return DailyData(hour, minute, title, body)
    }
    fun clear() {
        prefs.edit { clear() }
    }
}