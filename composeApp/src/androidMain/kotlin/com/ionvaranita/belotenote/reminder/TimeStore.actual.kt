package com.ionvaranita.belotenote.reminder

import android.content.Context
import com.ionvaranita.belotenote.reminder.TimeStoreAndroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual object TimeStore {
    actual suspend fun save(time: ReminderTime) { withContext(Dispatchers.Default) { TimeStoreAndroid.writeSync(ReminderSchedulerAndroidHolder.ctx(), time) } }
    actual suspend fun read(): ReminderTime? = withContext(Dispatchers.Default) { TimeStoreAndroid.readSync(ReminderSchedulerAndroidHolder.ctx()) }
    actual suspend fun clear() { withContext(Dispatchers.Default) { TimeStoreAndroid.clearSync(ReminderSchedulerAndroidHolder.ctx()) } }
}

internal object ReminderSchedulerAndroidHolder {
    private lateinit var context: Context
    private lateinit var mainActivityClass: Class<*>
    fun init(ctx: Context, cls: Class<*>) { context = ctx.applicationContext; mainActivityClass = cls }
    fun ctx(): Context = context
    fun activityClass(): Class<*> = mainActivityClass
}