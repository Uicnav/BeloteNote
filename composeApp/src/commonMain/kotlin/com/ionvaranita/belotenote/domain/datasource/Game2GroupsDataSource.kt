package com.ionvaranita.belotenote.domain.datasource

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import kotlinx.coroutines.flow.Flow

interface Game2GroupsDataSource {
    suspend fun getGames(): Flow<List<Game2GroupsEntity>>
    suspend fun insertGame(game: Game2GroupsEntity)
}