package com.ionvaranita.belotenote.datalayer.database.dao.groups2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.utils.getCurrentTime
import kotlinx.coroutines.flow.Flow

/**
 * Created by ionvaranita on 11/12/2017.
 */
@Dao
interface Game2GroupsDao {
    @Query("select * from Game2GroupsEntity order by dateGame asc")
    fun getGames(): Flow<List<Game2GroupsEntity>>

    @Query("select * from Game2GroupsEntity where idGame = :idGame")
    fun getGame(idGame: Int): Flow<Game2GroupsEntity>

    @Insert
    suspend fun insert(game2GroupsEntity: Game2GroupsEntity): Long

    @Query("delete from Game2GroupsEntity  where idGame = :idGame")
    suspend fun delete(idGame: Int)

    @Query("update Game2GroupsEntity set statusGame = :statusGame, scoreName1 = :scoreName1,dateGame = :dateGame  where idGame = :idGame")
    suspend fun updateStatusAndScoreName1(
        idGame: Int,
        statusGame: Byte = GameStatus.FINISHED.id,
        scoreName1: Short,
        dateGame: Long = getCurrentTime()
    ): Int

    @Query("update Game2GroupsEntity set statusGame = :statusGame, scoreName2 = :scoreName2,dateGame = :dateGame   where idGame = :idGame")
    suspend fun updateStatusAndScoreName2(
        idGame: Int,
        statusGame: Byte = GameStatus.FINISHED.id,
        scoreName2: Short,
        dateGame: Long = getCurrentTime()
    ): Int

    @Query("update Game2GroupsEntity set statusGame = :statusGame,dateGame = :dateGame  where idGame = :idGame")
    suspend fun updateOnlyStatus(
        idGame: Int,
        statusGame: Byte,
        dateGame: Long = getCurrentTime()
    ): Int

    @Query("update Game2GroupsEntity set statusGame = :statusGame, winningPoints= :winningPoints,dateGame = :dateGame  where idGame = :idGame")
    suspend fun updateStatusWinningPoints(
        idGame: Int,
        statusGame: Byte,
        winningPoints: Short,
        dateGame: Long = getCurrentTime()
    ): Int
}