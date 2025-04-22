package varanita.informatics.shared.database.dao.players2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Created by ionvaranita on 2019-08-29;
 */
@Dao
interface Scor2PDao {
    @Insert
    suspend fun insert(scor2PEntity: varanita.informatics.shared.database.entity.players2.Scor2PEntity)

    @Query("select *  from Scor2PEntity where idGame = :idGame")
    suspend fun getScore(idGame: Short): varanita.informatics.shared.database.entity.players2.Scor2PEntity

    @Update
    suspend fun update(scor2PEntity: varanita.informatics.shared.database.entity.players2.Scor2PEntity): Int

    @Query("delete from Scor2PEntity where idGame = :idGame")
    suspend fun delete(idGame: Short)
}