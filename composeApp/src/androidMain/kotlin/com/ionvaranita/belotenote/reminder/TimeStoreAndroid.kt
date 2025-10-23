package com.ionvaranita.belotenote.reminder

import android.content.Context
import androidx.core.content.edit

internal object TimeStoreAndroid {
    private const val P = "bn_time_dp"
    private const val H = "h"
    private const val M = "m"
    private fun dp(ctx: Context): Context {
        val dpc = ctx.createDeviceProtectedStorageContext()
        if (!dpc.getSharedPreferences(P, Context.MODE_PRIVATE).contains(H) && ctx.getSharedPreferences(P, Context.MODE_PRIVATE).contains(H)) {
            dpc.moveSharedPreferencesFrom(ctx, P)
        }
        return dpc
    }
    fun writeSync(context: Context, t: ReminderTime) {
        dp(context).getSharedPreferences(P, Context.MODE_PRIVATE).edit {
            putInt(H, t.hour).putInt(
                M,
                t.minute
            )
        }
    }
    fun readSync(context: Context): ReminderTime? {
        val sp = dp(context).getSharedPreferences(P, Context.MODE_PRIVATE)
        if (!sp.contains(H) || !sp.contains(M)) return null
        return ReminderTime(sp.getInt(H, 20), sp.getInt(M, 0))
    }
    fun clearSync(context: Context) {
        dp(context).getSharedPreferences(P, Context.MODE_PRIVATE).edit { clear() }
    }
}