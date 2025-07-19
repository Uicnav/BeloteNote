package varanita.informatics.shared.database.dao.players2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Game2PDao {
    @Insert
    suspend fun insert(game2PEntity: Game2PEntity) : Long

    @Query("select * from Game2PEntity order by dateGame desc")
    fun getGames(): Flow<List<Game2PEntity>>

    @Query("select * from Game2PEntity where idGame = :idGame")
    fun getGame(idGame: Int): Flow<Game2PEntity>

    @Query("update Game2PEntity set statusGame = :statusGame, scoreName1 = :scoreName1  where idGame = :idGame")
    suspend fun updateStatusAndScoreName1(idGame: Int, statusGame: Byte  = GameStatus.FINISHED.id, scoreName1: Short): Int

    @Query("update Game2PEntity set statusGame = :statusGame, scoreName2 = :scoreName2  where idGame = :idGame")
    suspend fun updateStatusAndScoreName2(idGame: Int, statusGame: Byte  = GameStatus.FINISHED.id, scoreName2: Short): Int

    @Query("update Game2PEntity set statusGame = :statusGame, winningPoints= :winningPoints  where idGame = :idGame")
    suspend fun updateStatusWinningPoints(idGame: Int, statusGame: Byte, winningPoints: Short): Int
    @Query("update Game2PEntity set statusGame = :statusGame  where idGame = :idGame")
    suspend fun updateOnlyStatus(idGame: Int, statusGame: Byte): Int

    @Query("update Game2PEntity set statusGame = :statusGame, winningPoints = :winningPoints, dateGame = :dateGame where idGame = :idGame")
    suspend fun update(idGame: Int, statusGame: Byte, winningPoints: Short, dateGame: Long): Int

    @Query("delete from Game2PEntity where idGame = :idGame")
    suspend fun delete(idGame: Int)
}