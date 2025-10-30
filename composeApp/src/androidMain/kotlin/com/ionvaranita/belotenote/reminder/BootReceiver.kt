package com.ionvaranita.belotenote.reminder

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.RequiresPermission
import com.ionvaranita.belotenote.scheduleNotification
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    private val ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED"
    private val TAG = this::class.java.canonicalName
    @OptIn(DelicateCoroutinesApi::class)
    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "BootReceiver onReceive intent.action = ${intent.action}")
        if (intent.action == ACTION_BOOT_COMPLETED) {
            GlobalScope.launch(Default) {
                scheduleNotification()
            }
        }
    }
}