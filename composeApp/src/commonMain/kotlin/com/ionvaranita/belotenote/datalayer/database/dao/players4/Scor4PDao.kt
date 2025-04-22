package varanita.informatics.shared.database.dao.players4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Scor4PEntity

/**
 * Created by ionvaranita on 2019-09-11;
 */
@Dao
interface Scor4PDao {
    @Insert
    suspend fun insert(scor4PEntity: Scor4PEntity)

    @Query("select *  from Scor4PEntity where idGame = :idGame")
   suspend fun getScore(idGame: Short): Scor4PEntity

    @Update
    suspend fun update(scor3PEntity: Scor4PEntity): Int

    @Query("delete from Scor4PEntity where idGame = :idGame")
    suspend fun delete(idGame: Short)
}