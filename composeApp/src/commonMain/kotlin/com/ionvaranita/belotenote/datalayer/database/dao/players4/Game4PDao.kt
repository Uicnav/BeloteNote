package varanita.informatics.shared.database.dao.players4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by ionvaranita on 2019-09-11;
 */
@Dao
interface Game4PDao {
    @Insert
    suspend fun insert(game4PEntity: Game4PEntity): Long

    @Query("select * from Game4PEntity order by dateGame desc")
    fun getGames(): Flow<List<Game4PEntity>>

    @Query("select * from Game4PEntity where idGame = :idGame")
    suspend fun getGame(idGame: Int): Game4PEntity

    @Query("update Game4PEntity set statusGame = :statusGame, dateGame = :dateGame where idGame = :idGame")
    suspend fun updateStatus(idGame: Int, statusGame: Byte, dateGame: Long): Int

    @Query("update Game4PEntity set statusGame = :statusGame, winnerPoints = :winnerPoints, dateGame = :dateGame where idGame = :idGame")
    suspend fun update(idGame: Int, statusGame: Byte, winnerPoints: Short, dateGame: Long): Int

    @Query("delete from Game4PEntity where idGame = :idGame")
    suspend fun delete(idGame: Int)
}