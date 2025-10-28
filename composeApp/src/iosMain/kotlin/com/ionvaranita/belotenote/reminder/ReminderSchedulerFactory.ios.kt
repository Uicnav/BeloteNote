package com.ionvaranita.belotenote.reminder

actual object ReminderSchedulerFactory {
    actual fun create(): ReminderScheduler = IosReminderSchedulerImpl()
}