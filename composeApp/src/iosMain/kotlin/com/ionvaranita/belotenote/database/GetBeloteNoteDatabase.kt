package com.ionvaranita.belotenote.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import com.ionvaranita.belotenote.utils.getCurrentTime
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSFileManager

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/BeloteNoteDatabase.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
                                            ).fallbackToDestructiveMigration(true).addCallback(object:RoomDatabase.Callback() {
        override fun onCreate(db: SQLiteConnection) {
            super.onCreate(db)
            val now = getCurrentTime()
            db.execSQL("INSERT INTO WinningPointsEntity (winningPoints, date) VALUES (101, ${now})")
            db.execSQL("INSERT INTO WinningPointsEntity (winningPoints, date) VALUES (51, ${now})")
        }
    })
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
                                                                        )
    return requireNotNull(documentDirectory?.path)
}
