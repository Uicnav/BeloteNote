package com.ionvaranita.belotenote.datalayer.repo.game

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game3PDataSource
import com.ionvaranita.belotenote.domain.repo.game.Games3PRepository
import kotlinx.coroutines.flow.Flow

class Games3PRepositoryImpl(private val games3PDataSource: Game3PDataSource) : Games3PRepository {
    override suspend fun getGames(): Flow<List<Game3PEntity>> {
        return games3PDataSource.getGames()
    }

    override suspend fun insetGame(game: Game3PEntity) {
        games3PDataSource.insertGame(game)
    }

    override suspend fun deleteGame(idGame: Int) {
        games3PDataSource.deleteGame(idGame)
    }

}