package com.ionvaranita.belotenote.datalayer.repo

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.domain.datasource.Game2GroupsDataSource
import com.ionvaranita.belotenote.domain.repo.Games2GroupsRepository
import kotlinx.coroutines.flow.Flow

class Games2GroupsRepositoryImpl(private val game2GroupsDataSource: Game2GroupsDataSource) : Games2GroupsRepository {
    override suspend fun getGames(): Flow<List<Game2GroupsEntity>> {
        return game2GroupsDataSource.getGames()
    }

    override suspend fun insetGame(game: Game2GroupsEntity) {
        game2GroupsDataSource.insertGame(game)
    }

}