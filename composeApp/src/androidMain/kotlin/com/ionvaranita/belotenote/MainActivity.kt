package com.ionvaranita.belotenote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import com.ionvaranita.belotenote.datalayer.database.getRoomDatabase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val appDatabase = getRoomDatabase(getDatabaseBuilder(applicationContext))
        setContent {
            enableEdgeToEdge()
            App(appDatabase = appDatabase, prefs = remember {
                createDataStore(applicationContext)
            })
        }
    }
}