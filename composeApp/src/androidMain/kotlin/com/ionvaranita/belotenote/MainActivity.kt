package com.ionvaranita.belotenote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ionvaranita.belotenote.datalayer.database.getRoomDatabase
import com.ionvaranita.belotenote.room_cmp.database.getDatabaseBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appDatabase = getRoomDatabase(getDatabaseBuilder(applicationContext) )

        setContent {
            App(appDatabase)
        }
    }
}