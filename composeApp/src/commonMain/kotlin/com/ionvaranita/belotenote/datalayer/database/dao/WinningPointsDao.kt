package com.ionvaranita.belotenote.datalayer.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.WinningPointsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WinningPointsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(winningPointsEntity: WinningPointsEntity)

    @Query("select * from WinningPointsEntity order by winningPoints desc")
    fun winnerPoints(): Flow<List<WinningPointsEntity>>
}
