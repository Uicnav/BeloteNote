package com.ionvaranita.belotenote.datalayer.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ionvaranita.belotenote.datalayer.database.dao.groups2.Game2GroupsDao
import com.ionvaranita.belotenote.datalayer.database.dao.groups2.Points2GroupsDao
import com.ionvaranita.belotenote.datalayer.database.dao.players3.Game3PDao
import com.ionvaranita.belotenote.datalayer.database.dao.players4.Points4PDao
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Points4PEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import varanita.informatics.shared.database.dao.players2.Game2PDao
import varanita.informatics.shared.database.dao.players2.Points2PDao
import varanita.informatics.shared.database.dao.players3.Points3PDao
import com.ionvaranita.belotenote.datalayer.database.dao.players4.Game4PDao

/**
 * Created by ionvaranita on 20/11/17.
 */
@Database(
    entities = [Game2PEntity::class, Points2PEntity::class, Game3PEntity::class, Points3PEntity::class, Game4PEntity::class, Points4PEntity::class, Game2GroupsEntity::class, Points2GroupsEntity::class],
    version = 16,
    exportSchema = false
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase(), DB {
    //PLAYERS 2
    abstract fun game2PDao(): Game2PDao

    abstract fun points2PDao(): Points2PDao

    //PLAYERS 3
    abstract fun game3PDao(): Game3PDao

    abstract fun points3PDao(): Points3PDao

    //GROUPS 2
    abstract fun points2GroupsDao(): Points2GroupsDao

    abstract fun game2GroupsDao(): Game2GroupsDao

    //PLAYERS 4
    abstract fun game4PDao(): Game4PDao

    abstract fun points4PDao(): Points4PDao

    override fun clearAllTables(): Unit {}
}

interface DB {
    fun clearAllTables(): Unit {}
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
    val database = builder.setDriver(BundledSQLiteDriver()).setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(true).build()
    return database
}





