package com.ionvaranita.belotenote.datalayer.database.dao.winningpoints

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.winningpoints.WinningPointsEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WinningPointsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(winningPointsEntity: WinningPointsEntity)

    @Query("select * from WinningPointsEntity order by date desc")
    fun getWinningPoints(): Flow<List<WinningPointsEntity>>
}
