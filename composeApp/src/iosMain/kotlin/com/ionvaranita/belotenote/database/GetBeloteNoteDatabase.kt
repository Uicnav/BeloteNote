package com.ionvaranita.belotenote.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSFileManager

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = documentDirectory() + "/BeloteNoteDatabase.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
                                            ).fallbackToDestructiveMigration(true)
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
