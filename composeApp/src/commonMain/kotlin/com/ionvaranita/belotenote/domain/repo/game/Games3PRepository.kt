package com.ionvaranita.belotenote.domain.repo.game

import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import kotlinx.coroutines.flow.Flow

interface Games3PRepository {
    suspend fun getGames(): Flow<List<Game3PEntity>>
    suspend fun getGame(idGame: Int): Flow<Game3PEntity>
    suspend fun insetGame(game: Game3PEntity): Int
    suspend fun deleteGame(idGame: Int)
    suspend fun updateStatusFinishedAndScoreName1(
        idGame: Int,
        statusGame: Byte = GameStatus.FINISHED.id,
        scoreName1: Short
    ): Int

    suspend fun updateStatusFinishedAndScoreName2(
        idGame: Int,
        statusGame: Byte = GameStatus.FINISHED.id,
        scoreName2: Short
    ): Int

    suspend fun updateStatusFinishedAndScoreName3(
        idGame: Int,
        statusGame: Byte = GameStatus.FINISHED.id,
        scoreName3: Short
    ): Int

    suspend fun updateStatusWinningPoints(idGame: Int, statusGame: Byte, winningPoints: Short)
    suspend fun updateOnlyStatus(idGame: Int, statusGame: Byte)
}