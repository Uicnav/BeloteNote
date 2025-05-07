package com.ionvaranita.belotenote.datalayer.repo

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game4PDataSource
import com.ionvaranita.belotenote.domain.repo.game.Games4PRepository
import kotlinx.coroutines.flow.Flow

class Games4PRepositoryImpl(private val games4PDataSource: Game4PDataSource) : Games4PRepository {
    override suspend fun getGames(): Flow<List<Game4PEntity>> {
        return games4PDataSource.getGames()
    }

    override suspend fun insetGame(game: Game4PEntity) {
        games4PDataSource.insertGame(game)
    }

    override suspend fun deleteGame(idGame: Int) {
        games4PDataSource.deleteGame(idGame)
    }

}