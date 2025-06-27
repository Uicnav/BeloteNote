package com.ionvaranita.belotenote.domain.datasource.game

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import kotlinx.coroutines.flow.Flow

interface Game2PDataSource {
    suspend fun getGame(idGame: Int): Flow<Game2PEntity>
    suspend fun getGames(): Flow<List<Game2PEntity>>
    suspend fun insertGame(game2PEntity: Game2PEntity): Int
    suspend fun deleteGame(idGame: Int)
    suspend fun updateStatusAndScoreName1(
        idGame: Int, statusGame: Byte, scoreName1: Short
    ): Int

    suspend fun updateStatusAndScoreName2(
        idGame: Int, statusGame: Byte, scoreName2: Short
    ): Int

    suspend fun updateStatusWinningPoints(idGame: Int, statusGame: Byte, winningPoints: Short)
    suspend fun updateOnlyStatus(idGame: Int, statusGame: Byte)
}