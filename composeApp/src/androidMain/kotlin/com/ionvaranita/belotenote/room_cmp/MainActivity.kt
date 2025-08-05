package com.ionvaranita.belotenote.room_cmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.ionvaranita.belotenote.App
import com.ionvaranita.belotenote.datalayer.database.getRoomDatabase
import com.ionvaranita.belotenote.room_cmp.database.getDatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val database = getRoomDatabase(getDatabaseBuilder(applicationContext))
        setContent {
            App(database)
        }
    }
}