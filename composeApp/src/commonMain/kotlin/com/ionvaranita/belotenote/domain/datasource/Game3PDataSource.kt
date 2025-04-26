package com.ionvaranita.belotenote.domain.datasource

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import kotlinx.coroutines.flow.Flow

interface Game3PDataSource {
    suspend fun getGames(): Flow<List<Game3PEntity>>
    suspend fun insertGame(game: Game3PEntity)
}