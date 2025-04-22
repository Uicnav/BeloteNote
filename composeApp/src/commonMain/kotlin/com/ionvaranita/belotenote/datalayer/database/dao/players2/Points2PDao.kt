package varanita.informatics.shared.database.dao.players2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by ionvaranita on 2019-08-26;
 */
@Dao
interface Points2PDao {
    @Insert
    suspend fun insert(points2PEntity: varanita.informatics.shared.database.entity.players2.Points2PEntity): Long

    @Query("select * from Points2PEntity where idGame = :idGame")
    suspend fun getPoints(idGame: Short): List<varanita.informatics.shared.database.entity.players2.Points2PEntity>

    @Query("select * from Points2PEntity where id = (select max(id) from Points2PEntity where idGame = :idGame)")
    suspend fun getLastPoints(idGame: Short): varanita.informatics.shared.database.entity.players2.Points2PEntity

    @Query("delete from Points2PEntity where idGame = :idGame")
    suspend fun delete(idGame: Short)
}
