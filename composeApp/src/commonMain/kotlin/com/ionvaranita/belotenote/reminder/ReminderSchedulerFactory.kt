package com.ionvaranita.belotenote.reminder

expect object ReminderSchedulerFactory {
    fun create(): ReminderScheduler
}