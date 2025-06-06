package varanita.informatics.shared.database.dao.groups2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by ionvaranita on 11/12/2017.
 */
@Dao
interface Game2GroupsDao {
    @Query("select * from Game2GroupsEntity order by dateGame desc")
    fun getGames(): Flow<List<Game2GroupsEntity>>

    @Query("select * from Game2GroupsEntity where idGame = :idGame")
    fun getGame(idGame: Int): Flow<Game2GroupsEntity>

    @Insert
    suspend fun insert(game2GroupsEntity: Game2GroupsEntity): Long

    @Query("update Game2GroupsEntity set statusGame = :statusGame, winnerPoints = :winnerPoints, dateGame = :dateGame where idGame = :idGame")
    suspend fun update(idGame: Int, statusGame: Byte, winnerPoints: Short, dateGame: Long) : Int

    @Query("update Game2GroupsEntity set statusGame = :statusGame, dateGame = :dateGame where idGame = :idGame")
    suspend fun updateStatus(idGame: Int, statusGame: Byte, dateGame: Long): Int

    @Query("delete from Game2GroupsEntity  where idGame = :idGame")
    suspend fun delete(idGame: Int)

    @Query("update Game2GroupsEntity set statusGame = :statusGame, scoreName1 = :scoreName1  where idGame = :idGame")
    suspend fun updateStatusAndScoreName1(idGame: Int, statusGame: Byte  = GameStatus.FINISHED.id, scoreName1: Short): Int

    @Query("update Game2GroupsEntity set statusGame = :statusGame, scoreName2 = :scoreName2   where idGame = :idGame")
    suspend fun updateStatusAndScoreName2(idGame: Int, statusGame: Byte  = GameStatus.FINISHED.id, scoreName2: Short): Int

    @Query("update Game2GroupsEntity set statusGame = :statusGame  where idGame = :idGame")
    suspend fun updateOnlyStatus(idGame: Int, statusGame: Byte): Int
}