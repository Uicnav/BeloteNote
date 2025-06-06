package com.ionvaranita.belotenote.datalayer.datasource.game

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game3PDataSource
import kotlinx.coroutines.flow.Flow
import com.ionvaranita.belotenote.datalayer.database.dao.players3.Game3PDao

class Game3PDataSourceImpl(private val dao: Game3PDao) : Game3PDataSource {
    override suspend fun getGames(): Flow<List<Game3PEntity>> {
        return dao.getGames()
    }

    override suspend fun insertGame(game: Game3PEntity) {
        dao.insert(game)
    }

    override suspend fun deleteGame(idGame: Int) {
        dao.delete(idGame)
    }

    override suspend fun getGame(idGame: Int): Flow<Game3PEntity> {
       return dao.getGame(idGame)
    }

}