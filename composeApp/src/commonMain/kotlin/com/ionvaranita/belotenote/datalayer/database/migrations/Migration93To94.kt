package com.ionvaranita.belotenote.datalayer.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.ionvaranita.belotenote.utils.getCurrentTime

val MIGRATION_93_94 = object : Migration(93, 94) {
    override fun migrate(database: SQLiteConnection) {
        val now = getCurrentTime()
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS WinningPointsEntity (
                winningPoints INTEGER PRIMARY KEY NOT NULL,
                date INTEGER NOT NULL
            )
            """
        )
        database.execSQL("INSERT INTO WinningPointsEntity (winningPoints, date) VALUES (101, $now)")
        database.execSQL("INSERT INTO WinningPointsEntity (winningPoints, date) VALUES (51, $now)")
    }
}
