package com.ionvaranita.belotenote.datalayer.datasource

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game2PDataSource
import kotlinx.coroutines.flow.Flow
import varanita.informatics.shared.database.dao.players2.Game2PDao

class Game2PDataSourceImpl(private val game2PDao: Game2PDao): Game2PDataSource {
    override suspend fun getGames(): Flow<List<Game2PEntity>> {
        return game2PDao.getGames()
    }

    override suspend fun insertGame(game2PEntity: Game2PEntity) {
        game2PDao.insert(game2PEntity)
    }

    override suspend fun deleteGame(idGame: Int) {
        game2PDao.delete(idGame)
    }

}