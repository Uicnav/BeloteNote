package com.ionvaranita.belotenote.datalayer.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ionvaranita.belotenote.datalayer.database.dao.WinnerPointsDao
import com.ionvaranita.belotenote.datalayer.database.dao.groups2.Scor2GroupsDao
import com.ionvaranita.belotenote.datalayer.database.dao.players4.Points4PDao
import com.ionvaranita.belotenote.datalayer.database.entity.BoltEntity
import com.ionvaranita.belotenote.datalayer.database.entity.BoltManagerEntity
import com.ionvaranita.belotenote.datalayer.database.entity.ExtendedGameEntity
import com.ionvaranita.belotenote.datalayer.database.entity.WinnerPointsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Scor2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Scor3PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Points4PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Scor4PEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import varanita.informatics.shared.database.dao.BoltDao
import varanita.informatics.shared.database.dao.BoltManagerDao
import varanita.informatics.shared.database.dao.ExtendedGameDao
import varanita.informatics.shared.database.dao.groups2.Game2GroupsDao
import varanita.informatics.shared.database.dao.groups2.Points2GroupsDao
import varanita.informatics.shared.database.dao.players2.Game2PDao
import varanita.informatics.shared.database.dao.players2.Points2PDao
import varanita.informatics.shared.database.dao.players2.Scor2PDao
import varanita.informatics.shared.database.dao.players3.Game3PDao
import varanita.informatics.shared.database.dao.players3.Points3PDao
import varanita.informatics.shared.database.dao.players3.Scor3PDao
import varanita.informatics.shared.database.dao.players4.Game4PDao
import varanita.informatics.shared.database.dao.players4.Scor4PDao
import varanita.informatics.shared.database.entity.players2.Points2PEntity

/**
 * Created by ionvaranita on 20/11/17.
 */
@Database(
    entities = [Game2PEntity::class, Points2PEntity::class, varanita.informatics.shared.database.entity.players2.Scor2PEntity::class, Game3PEntity::class, Points3PEntity::class, Scor3PEntity::class, Game4PEntity::class, Points4PEntity::class, Scor4PEntity::class, Game2GroupsEntity::class, Points2GroupsEntity::class, Scor2GroupsEntity::class, ExtendedGameEntity::class, BoltEntity::class, BoltManagerEntity::class, WinnerPointsEntity::class],
    version = 1, exportSchema = false)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase(), DB {
    //PLAYERS 2
    abstract fun game2PDao(): Game2PDao

    abstract fun points2PDao(): Points2PDao

    abstract fun scor2PDao(): Scor2PDao

    //PLAYERS 3
    abstract fun game3PDao(): Game3PDao

    abstract fun points3PDao(): Points3PDao

    abstract fun scor3PDao(): Scor3PDao

    //GROUPS 2
    abstract fun points2GroupsDao(): Points2GroupsDao

    abstract fun scor2GroupsDao(): Scor2GroupsDao

    abstract fun game2GroupsDao(): Game2GroupsDao

    //PLAYERS 4
    abstract fun game4PDao(): Game4PDao

    abstract fun points4PDao(): Points4PDao

    abstract fun scor4PDao(): Scor4PDao

    //GLOBAL
    abstract fun winnerPointsDao(): WinnerPointsDao

    abstract fun boltDao(): BoltDao

    abstract fun boltManagerDao(): BoltManagerDao

    abstract fun extendedGameDao(): ExtendedGameDao

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
    return builder.setDriver(BundledSQLiteDriver()).setQueryCoroutineContext(Dispatchers.IO).build()
}





