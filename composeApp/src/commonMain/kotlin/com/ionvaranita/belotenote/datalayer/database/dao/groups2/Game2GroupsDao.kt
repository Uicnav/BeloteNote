package varanita.informatics.shared.database.dao.groups2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity

/**
 * Created by ionvaranita on 11/12/2017.
 */
@Dao
interface Game2GroupsDao {
    @Query("select * from Game2GroupsEntity order by dateGame desc")
    suspend fun getGames(): List<Game2GroupsEntity>

    @Query("select * from Game2GroupsEntity where idGame = :idGame")
    suspend fun getGame(idGame: Short): Game2GroupsEntity

    @Insert
    suspend fun insert(game2GroupsEntity: Game2GroupsEntity): Long

    @Query("update Game2GroupsEntity set statusGame = :statusGame, winnerPoints = :winnerPoints, dateGame = :dateGame where idGame = :idGame")
    suspend fun update(idGame: Short, statusGame: Byte, winnerPoints: Short, dateGame: Long) : Int

    @Query("update Game2GroupsEntity set statusGame = :statusGame, dateGame = :dateGame where idGame = :idGame")
    suspend fun updateStatus(idGame: Short, statusGame: Byte, dateGame: Long): Int

    @Query("delete from Game2GroupsEntity  where idGame = :idGame")
    suspend fun delete(idGame: Short)
}