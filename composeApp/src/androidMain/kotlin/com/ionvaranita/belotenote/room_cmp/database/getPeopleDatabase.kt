package com.ionvaranita.belotenote.room_cmp.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ionvaranita.belotenote.datalayer.database.AppDatabase

fun getPeopleDatabase(context: Context): AppDatabase {
    val dbFile = context.getDatabasePath("belotenote.db")
    return Room.databaseBuilder<AppDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}