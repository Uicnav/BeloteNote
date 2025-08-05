package com.ionvaranita.belotenote.datalayer.database.dao.players3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Game3PDao {
    @Insert
    suspend fun insert(game: Game3PEntity): Long

    @Query("select * from Game3PEntity order by dateGame asc")
    fun getGames(): Flow<List<Game3PEntity>>

    @Query("select * from Game3PEntity where idGame = :idGame")
    fun getGame(idGame: Int): Flow<Game3PEntity>

    @Query("update Game3PEntity set statusGame = :statusGame, dateGame = :dateGame where idGame = :idGame")
    suspend fun updateStatus(idGame: Int, statusGame: Byte, dateGame: Long): Int

    @Query("delete from Game3PEntity where idGame = :idGame")
    suspend fun delete(idGame: Int)

    @Query("update Game3PEntity set statusGame = :statusGame, scoreName1 = :scoreName1  where idGame = :idGame")
    suspend fun updateStatusAndScoreName1(idGame: Int, statusGame: Byte  = GameStatus.FINISHED.id, scoreName1: Short): Int

    @Query("update Game3PEntity set statusGame = :statusGame, scoreName2 = :scoreName2  where idGame = :idGame")
    suspend fun updateStatusAndScoreName2(idGame: Int, statusGame: Byte  = GameStatus.FINISHED.id, scoreName2: Short): Int

    @Query("update Game3PEntity set statusGame = :statusGame, scoreName3 = :scoreName3  where idGame = :idGame")
    suspend fun updateStatusAndScoreName3(idGame: Int, statusGame: Byte  = GameStatus.FINISHED.id, scoreName3: Short): Int

    @Query("update Game3PEntity set statusGame = :statusGame, winningPoints= :winningPoints  where idGame = :idGame")
    suspend fun updateStatusWinningPoints(idGame: Int, statusGame: Byte, winningPoints: Short): Int
    @Query("update Game3PEntity set statusGame = :statusGame  where idGame = :idGame")
    suspend fun updateOnlyStatus(idGame: Int, statusGame: Byte): Int

    @Query("update Game3PEntity set statusGame = :statusGame, winningPoints = :winningPoints, dateGame = :dateGame where idGame = :idGame")
    suspend fun update(idGame: Int, statusGame: Byte, winningPoints: Short, dateGame: Long): Int

}