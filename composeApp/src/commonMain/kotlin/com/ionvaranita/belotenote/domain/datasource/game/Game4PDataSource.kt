package com.ionvaranita.belotenote.domain.datasource.game

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import kotlinx.coroutines.flow.Flow

interface Game4PDataSource {
    suspend fun getGames(): Flow<List<Game4PEntity>>
    suspend fun insertGame(game: Game4PEntity)
    suspend fun deleteGame(idGame: Int)
}