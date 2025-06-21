package com.ionvaranita.belotenote.domain.repo.game

import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import kotlinx.coroutines.flow.Flow

interface Games2GroupsRepository {
    suspend fun getGame(idGame: Int): Flow<Game2GroupsEntity>
    suspend fun getGames(): Flow<List<Game2GroupsEntity>>
    suspend fun insetGame(game: Game2GroupsEntity)
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
    suspend fun updateOnlyStatus(idGame: Int, gameStatus: Byte)

    suspend fun updateStatusWinningPoints(idGame: Int, gameStatus: Byte, winningPoints: Short)
}