package com.ionvaranita.belotenote.datalayer.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.WinnerPointsEntity

@Dao
interface WinnerPointsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(winnerPointsEntity: WinnerPointsEntity)

    @Query("select winningPoints from WinnerPointsEntity order by winningPoints desc")
    suspend fun winnerPoints(): List<Short>
}
