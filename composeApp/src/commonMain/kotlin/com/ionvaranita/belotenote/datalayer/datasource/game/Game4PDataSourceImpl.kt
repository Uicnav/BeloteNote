package com.ionvaranita.belotenote.datalayer.datasource.game

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game4PDataSource
import kotlinx.coroutines.flow.Flow
import varanita.informatics.shared.database.dao.players4.Game4PDao

class Game4PDataSourceImpl(private val dao: Game4PDao) : Game4PDataSource {
    override suspend fun getGames(): Flow<List<Game4PEntity>> {
        return dao.getGames()
    }

    override suspend fun insertGame(game: Game4PEntity): Int {
        return dao.insert(game).toInt()
    }

    override suspend fun deleteGame(idGame: Int) {
        dao.delete(idGame)
    }

}