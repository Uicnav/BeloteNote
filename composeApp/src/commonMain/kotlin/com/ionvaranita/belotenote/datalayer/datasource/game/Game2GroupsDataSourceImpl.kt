package com.ionvaranita.belotenote.datalayer.datasource.game

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game2GroupsDataSource
import kotlinx.coroutines.flow.Flow
import varanita.informatics.shared.database.dao.groups2.Game2GroupsDao

class Game2GroupsDataSourceImpl(private val dao: Game2GroupsDao) : Game2GroupsDataSource {
    override suspend fun getGame(idGame: Int): Flow<Game2GroupsEntity> {
        return dao.getGame(idGame)
    }

    override suspend fun getGames(): Flow<List<Game2GroupsEntity>> {
        return dao.getGames()
    }

    override suspend fun insertGame(game: Game2GroupsEntity) {
        dao.insert(game)
    }

    override suspend fun deleteGame(idGame: Int) {
        dao.delete(idGame)
    }

    override suspend fun updateStatusFinishedAndScoreName1(
        idGame: Int,
        statusGame: Byte,
        scoreName1: Short
    ): Int {
        return dao.updateStatusAndScoreName1(idGame, statusGame, scoreName1)
    }

    override suspend fun updateStatusFinishedAndScoreName2(
        idGame: Int,
        statusGame: Byte,
        scoreName2: Short
    ): Int {
        return dao.updateStatusAndScoreName2(idGame, statusGame, scoreName2)
    }

    override suspend fun updateOnlyStatus(idGame: Int, gameStatus: Byte) {
        dao.updateOnlyStatus(idGame, gameStatus)
    }
}