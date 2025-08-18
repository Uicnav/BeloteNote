package com.ionvaranita.belotenote.datalayer.database.dao.players4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.utils.getCurrentTime
import kotlinx.coroutines.flow.Flow

/**
 * Created by ionvaranita on 2019-09-11;
 */
@Dao
interface Game4PDao {
    @Insert
    suspend fun insert(game4PEntity: Game4PEntity): Long

    @Query("select * from Game4PEntity order by dateGame asc")
    fun getGames(): Flow<List<Game4PEntity>>

    @Query("select * from Game4PEntity where idGame = :idGame")
    fun getGame(idGame: Int): Flow<Game4PEntity>

    @Query("delete from Game4PEntity where idGame = :idGame")
    suspend fun delete(idGame: Int)

    @Query("update Game4PEntity set statusGame = :statusGame, scoreName1 = :scoreName1 ,dateGame = :dateGame where idGame = :idGame")
    suspend fun updateStatusAndScoreName1(
        idGame: Int,
        statusGame: Byte = GameStatus.FINISHED.id,
        scoreName1: Short,
        dateGame: Long = getCurrentTime()
    ): Int

    @Query("update Game4PEntity set statusGame = :statusGame, scoreName2 = :scoreName2,dateGame = :dateGame  where idGame = :idGame")
    suspend fun updateStatusAndScoreName2(
        idGame: Int,
        statusGame: Byte = GameStatus.FINISHED.id,
        scoreName2: Short,
        dateGame: Long = getCurrentTime()
    ): Int

    @Query("update Game4PEntity set statusGame = :statusGame, scoreName3 = :scoreName3,dateGame = :dateGame  where idGame = :idGame")
    suspend fun updateStatusAndScoreName3(
        idGame: Int,
        statusGame: Byte = GameStatus.FINISHED.id,
        scoreName3: Short,
        dateGame: Long = getCurrentTime()
    ): Int

    @Query("update Game4PEntity set statusGame = :statusGame, scoreName4 = :scoreName4,dateGame = :dateGame  where idGame = :idGame")
    suspend fun updateStatusAndScoreName4(
        idGame: Int,
        statusGame: Byte = GameStatus.FINISHED.id,
        scoreName4: Short,
        dateGame: Long = getCurrentTime()
    ): Int


    @Query("update Game4PEntity set statusGame = :statusGame, winningPoints= :winningPoints,dateGame = :dateGame  where idGame = :idGame")
    suspend fun updateStatusWinningPoints(
        idGame: Int, statusGame: Byte, winningPoints: Short, dateGame: Long = getCurrentTime()
    ): Int

    @Query("update Game4PEntity set statusGame = :statusGame,dateGame = :dateGame  where idGame = :idGame")
    suspend fun updateOnlyStatus(
        idGame: Int, statusGame: Byte, dateGame: Long = getCurrentTime()
    ): Int
}