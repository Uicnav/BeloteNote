package com.ionvaranita.belotenote.datalayer.database.dao.players4

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Points4PEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by ionvaranita on 2019-09-11;
 */
@Dao
interface Points4PDao {
    @Insert
    suspend fun insert(entity: Points4PEntity): Long

    @Query("select * from Points4PEntity where idGame = :idGame")
    fun getPoints(idGame: Int): Flow<List<Points4PEntity>>

    @Query("delete from Points4PEntity where idGame = :idGame")
    suspend fun deleteByIdGame(idGame: Int): Int

    @Delete
    suspend fun deleteRow(entity: Points4PEntity)
}
