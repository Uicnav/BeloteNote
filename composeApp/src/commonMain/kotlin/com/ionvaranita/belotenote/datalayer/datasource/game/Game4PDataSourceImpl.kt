package com.ionvaranita.belotenote.datalayer.datasource.game

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game4PDataSource
import kotlinx.coroutines.flow.Flow
import varanita.informatics.shared.database.dao.players4.Game4PDao

class Game4PDataSourceImpl(private val game4PDao: Game4PDao) : Game4PDataSource {
    override suspend fun getGames(): Flow<List<Game4PEntity>> {
        return game4PDao.getGames()
    }

    override suspend fun insertGame(game: Game4PEntity) {
        game4PDao.insert(game)
    }

    override suspend fun deleteGame(idGame: Int) {
        game4PDao.delete(idGame)
    }

}