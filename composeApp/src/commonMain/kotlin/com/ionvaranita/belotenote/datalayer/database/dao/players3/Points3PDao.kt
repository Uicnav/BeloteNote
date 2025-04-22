package varanita.informatics.shared.database.dao.players3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity

/**
 * Created by ionvaranita on 2019-09-05;
 */
@Dao
interface Points3PDao {
    @Insert
    suspend fun insert(Points3PEntity: Points3PEntity): Long

    @Query("select * from Points3PEntity where idGame = :idGame")
    suspend fun getPoints(idGame: Short): List<Points3PEntity>

    @Query("select * from Points3PEntity where id = (select max(id) from Points3PEntity where idGame = :idGame)")
    suspend fun getLastPoints(idGame: Short): Points3PEntity

    @Query("delete from Points3PEntity where idGame = :idGame")
    suspend fun deletePoints(idGame: Short)
}
