package varanita.informatics.shared.database.dao.players2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Game2PDao {
    @Insert
    suspend fun insert(game2PEntity: Game2PEntity): Long

    @Query("select * from Game2PEntity order by dateGame desc")
    fun getGames(): Flow<List<Game2PEntity>>

    @Query("select * from Game2PEntity where idGame = :idGame")
    suspend fun getGame(idGame: Short): Game2PEntity

    @Query("update Game2PEntity set statusGame = :statusGame, dateGame = :dateGame where idGame = :idGame")
    suspend fun updateStatus(idGame: Short, statusGame: Byte, dateGame: Long): Int

    @Query("update Game2PEntity set statusGame = :statusGame, winnerPoints = :winnerPoints, dateGame = :dateGame where idGame = :idGame")
    suspend fun update(idGame: Short, statusGame: Byte, winnerPoints: Short, dateGame: Long): Int

    @Query("delete from Game2PEntity where idGame = :idGame")
    suspend fun delete(idGame: Short)
}