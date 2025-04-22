package com.ionvaranita.belotenote.domain.repo

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import kotlinx.coroutines.flow.Flow

interface Games2PRepository {
    suspend fun getGames(): Flow<List<Game2PEntity>>
    suspend fun insetGame(game2PEntity: Game2PEntity)
}