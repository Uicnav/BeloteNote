package com.ionvaranita.belotenote

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import com.ionvaranita.belotenote.datalayer.database.getRoomDatabase


class MainActivity : ComponentActivity() {
    private val TAG = this::class.java.canonicalName
    private val requestNotif =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            Log.d(TAG, "requestNotif permission granted = $granted")
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= 33) {
            requestNotif.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        val appDatabase = getRoomDatabase(getDatabaseBuilder(applicationContext))
        setContent {
            enableEdgeToEdge()
            App(appDatabase = appDatabase, prefs = remember {
                createDataStore(applicationContext)
            })
        }
    }
}