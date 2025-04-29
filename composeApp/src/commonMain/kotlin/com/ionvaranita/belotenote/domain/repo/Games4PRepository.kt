package com.ionvaranita.belotenote.domain.repo

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import kotlinx.coroutines.flow.Flow

interface Games4PRepository {
    suspend fun getGames(): Flow<List<Game4PEntity>>
    suspend fun insetGame(game: Game4PEntity)
}