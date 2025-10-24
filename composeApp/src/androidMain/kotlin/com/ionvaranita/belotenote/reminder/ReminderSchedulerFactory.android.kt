package com.ionvaranita.belotenote.reminder

import android.content.Context
import androidx.startup.Initializer

object AndroidContextHolder {
    lateinit var appContext: Context
}

class AndroidContextInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        AndroidContextHolder.appContext = context.applicationContext
    }
    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}

actual object ReminderSchedulerFactory {
    actual fun create(): ReminderScheduler = AndroidReminderSchedulerImpl(AndroidContextHolder.appContext)
}