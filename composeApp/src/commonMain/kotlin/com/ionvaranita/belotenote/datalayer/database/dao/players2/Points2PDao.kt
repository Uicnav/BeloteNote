package varanita.informatics.shared.database.dao.players2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by ionvaranita on 2019-08-26;
 */
@Dao
interface Points2PDao {
    @Insert
    suspend fun insert(points2PEntity: Points2PEntity): Long

    @Query("select * from Points2PEntity where idGame = :idGame")
    fun getPoints(idGame: Int): Flow<List<Points2PEntity>>

    @Query("select * from Points2PEntity where id = (select max(id) from Points2PEntity where idGame = :idGame)")
    suspend fun getLastPoints(idGame: Int): Points2PEntity?

    @Query("delete from Points2PEntity where idGame = :idGame")
    suspend fun deleteRow(idGame: Int): Int
    @Delete
    suspend fun deleteRow(row: Points2PEntity)
}
