package varanita.informatics.shared.database.dao.players3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Scor3PEntity

/**
 * Created by ionvaranita on 2019-09-06;
 */
@Dao
interface Scor3PDao {
    @Insert
    suspend fun insert(scor3PEntity: Scor3PEntity)

    @Query("select *  from Scor3PEntity where idGame = :idGame")
    suspend fun getScore(idGame: Short): Scor3PEntity

    @Update
    suspend fun update(scor3PEntity: Scor3PEntity): Int

    @Query("delete from Scor3PEntity where idGame = :idGame")
    suspend fun delete(idGame: Short)
}