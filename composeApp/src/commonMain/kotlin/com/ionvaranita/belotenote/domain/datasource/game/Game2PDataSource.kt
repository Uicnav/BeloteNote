package com.ionvaranita.belotenote.domain.datasource.game

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import kotlinx.coroutines.flow.Flow

interface Game2PDataSource {
    suspend fun getGame(idGame: Int): Flow<Game2PEntity>
    suspend fun getGames(): Flow<List<Game2PEntity>>
    suspend fun insertGame(game2PEntity: Game2PEntity)
    suspend fun deleteGame(idGame: Int)
}