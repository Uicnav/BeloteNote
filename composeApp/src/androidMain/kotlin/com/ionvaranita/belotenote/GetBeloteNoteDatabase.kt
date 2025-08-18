package com.ionvaranita.belotenote

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.utils.getCurrentTime

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("BeloteNoteDatabase.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext, name = dbFile.absolutePath
    ).fallbackToDestructiveMigration(true).addCallback(object:RoomDatabase.Callback() {
        override fun onCreate(db: SQLiteConnection) {
            super.onCreate(db)
            val now = getCurrentTime()
            db.execSQL("INSERT INTO WinningPointsEntity (winningPoints, date) VALUES (101, ${now})")
            db.execSQL("INSERT INTO WinningPointsEntity (winningPoints, date) VALUES (51, ${now})")
        }
    })
}