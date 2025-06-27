package com.ionvaranita.belotenote.domain.repo.game

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import kotlinx.coroutines.flow.Flow

interface Games4PRepository {
    suspend fun getGames(): Flow<List<Game4PEntity>>
    suspend fun insertGame(game: Game4PEntity) : Int
    suspend fun deleteGame(idGame: Int)
}