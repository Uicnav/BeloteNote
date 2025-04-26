package com.ionvaranita.belotenote.domain.repo

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import kotlinx.coroutines.flow.Flow

interface Games3PRepository {
    suspend fun getGames(): Flow<List<Game3PEntity>>
    suspend fun insetGame(game: Game3PEntity)
}