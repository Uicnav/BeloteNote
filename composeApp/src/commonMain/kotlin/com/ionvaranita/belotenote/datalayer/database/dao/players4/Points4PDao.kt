package com.ionvaranita.belotenote.datalayer.database.dao.players4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Points4PEntity

/**
 * Created by ionvaranita on 2019-09-11;
 */
@Dao
interface Points4PDao {
    @Insert
    suspend fun insert(points4PEntity: Points4PEntity): Long

    @Query("select * from Points4PEntity where idGame = :idGame")
    suspend fun getPoints(idGame: Short): List<Points4PEntity>

    @Query("select * from Points4PEntity where id = (select max(id) from Points4PEntity where idGame = :idGame)")
    suspend fun getLastPoints(idGame: Short): Points4PEntity

    @Query("delete from Points4PEntity where idGame = :idGame")
    suspend fun deletePoints(idGame: Short)
}
