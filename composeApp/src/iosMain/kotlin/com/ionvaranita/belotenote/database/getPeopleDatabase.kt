package database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ionvaranita.belotenote.datalayer.database.AppDatabase
import platform.Foundation.NSHomeDirectory

fun getPeopleDatabase(): AppDatabase {
    val dbFile = NSHomeDirectory() + "/belote_note.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile,
        factory = { AppDatabase::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}