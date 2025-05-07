package varanita.informatics.shared.database.dao.players2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Scor2PEntity

/**
 * Created by ionvaranita on 2019-08-29;
 */
@Dao
interface Scor2PDao {
    @Insert
    suspend fun insert(scor2PEntity: Scor2PEntity)

    @Query("select *  from Scor2PEntity where idGame = :idGame")
    suspend fun getScore(idGame: Short): Scor2PEntity

    @Update
    suspend fun update(scor2PEntity: Scor2PEntity): Int

    @Query("delete from Scor2PEntity where idGame = :idGame")
    suspend fun delete(idGame: Short)
}