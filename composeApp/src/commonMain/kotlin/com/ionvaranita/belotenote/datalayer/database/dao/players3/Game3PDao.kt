package varanita.informatics.shared.database.dao.players3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity

@Dao
interface Game3PDao {
    @Insert
    suspend fun insert(game3PEntity: Game3PEntity): Long

    @Query("select * from Game3PEntity order by dateGame desc")
    suspend fun getGames(): List<Game3PEntity>

    @Query("select * from Game3PEntity where idGame = :idGame")
    suspend fun getGame(idGame: Short): Game3PEntity

    @Query("update Game3PEntity set statusGame = :statusGame, dateGame = :dateGame where idGame = :idGame")
    suspend fun updateStatus(idGame: Short, statusGame: Byte, dateGame: Long): Int

    @Query("update Game3PEntity set statusGame = :statusGame, winnerPoints = :winnerPoints, dateGame = :dateGame where idGame = :idGame")
    suspend fun update(idGame: Short, statusGame: Byte, winnerPoints: Short, dateGame: Long): Int

    @Query("delete from Game3PEntity where idGame = :idGame")
    suspend fun delete(idGame: Short)
}