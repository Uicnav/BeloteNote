package com.ionvaranita.belotenote.datalayer.database.dao.groups2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Scor2GroupsEntity

/**
 * Created by ionvaranita on 09/12/2017.
 */
@Dao
interface Scor2GroupsDao {
    @Insert
    suspend fun insert(scor2GroupsEntity: Scor2GroupsEntity)

    @Query("select *  from Scor2GroupsEntity where idGame = :idGame")
    suspend fun getScore(idGame: Short): Scor2GroupsEntity

    @Update
    suspend fun update(scor2GroupsEntity: Scor2GroupsEntity): Int

    @Query("delete from Scor2GroupsEntity where idGame = :idGame")
    suspend fun delete(idGame: Short)
}