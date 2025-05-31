package com.ionvaranita.belotenote.datalayer.database.dao.groups2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Bolt2GroupsEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by ionvaranita on 2019-09-02;
 */
@Dao
interface Bolt2GroupsDao {
    @Insert
    suspend fun insert(boltEntity: Bolt2GroupsEntity)

    @Query("SELECT COUNT(*) FROM Bolt2GroupsEntity WHERE idGame = :idGame AND idPersonBolt = :idPerson")
    suspend fun countBoltsByPerson(idGame: Int, idPerson: Int): Int

    @Query("delete from Bolt2GroupsEntity where idGame = :idGame")
    suspend fun delete(idGame: Int)

    @Query("select * from Bolt2GroupsEntity where idGame = :idGame")
    fun getBolts(idGame: Int): Flow<List<Bolt2GroupsEntity>>
}
