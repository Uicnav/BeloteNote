package com.ionvaranita.belotenote.domain.repo.game

import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import kotlinx.coroutines.flow.Flow

interface Games2PRepository {
    suspend fun getGame(idGame: Int): Flow<Game2PEntity>
    suspend fun getGames(): Flow<List<Game2PEntity>>
    suspend fun insetGame(game2PEntity: Game2PEntity)
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
    suspend fun updateStatusWinningPoints(idGame: Int, statusGame: Byte, winningPoints: Short)
    suspend fun updateOnlyStatus(idGame: Int, statusGame: Byte)
}