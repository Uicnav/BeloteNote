package com.ionvaranita.belotenote
import android.app.Application
import android.util.Log
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { _, e -> Log.e("FATAL", "uncaught", e) }
    }
}
