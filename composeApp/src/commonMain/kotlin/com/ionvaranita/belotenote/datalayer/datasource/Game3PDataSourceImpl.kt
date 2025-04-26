package com.ionvaranita.belotenote.datalayer.datasource

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.domain.datasource.Game3PDataSource
import kotlinx.coroutines.flow.Flow
import varanita.informatics.shared.database.dao.players3.Game3PDao

class Game3PDataSourceImpl(private val game3PDao: Game3PDao) : Game3PDataSource {
    override suspend fun getGames(): Flow<List<Game3PEntity>> {
        return game3PDao.getGames()
    }

    override suspend fun insertGame(game: Game3PEntity) {
        game3PDao.insert(game)
    }

}