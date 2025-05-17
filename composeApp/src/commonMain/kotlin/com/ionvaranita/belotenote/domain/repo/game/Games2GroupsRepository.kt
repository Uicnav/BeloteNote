package com.ionvaranita.belotenote.domain.repo.game

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import kotlinx.coroutines.flow.Flow

interface Games2GroupsRepository {
    suspend fun getGame(idGame: Int): Flow<Game2GroupsEntity>
    suspend fun getGames(): Flow<List<Game2GroupsEntity>>
    suspend fun insetGame(game: Game2GroupsEntity)
    suspend fun deleteGame(idGame: Int)
}