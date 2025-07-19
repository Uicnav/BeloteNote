package com.ionvaranita.belotenote.datalayer.datasource.game

import com.ionvaranita.belotenote.datalayer.database.dao.players4.Game4PDao
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game4PDataSource
import kotlinx.coroutines.flow.Flow

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

    override suspend fun getGame(idGame: Int): Flow<Game4PEntity> {
        return dao.getGame(idGame)
    }

    override suspend fun updateStatusFinishedAndScoreName1(
        idGame: Int, statusGame: Byte, scoreName1: Short
    ): Int {
        return dao.updateStatusAndScoreName1(idGame, statusGame, scoreName1)
    }

    override suspend fun updateStatusFinishedAndScoreName2(
        idGame: Int, statusGame: Byte, scoreName2: Short
    ): Int {
        return dao.updateStatusAndScoreName2(idGame, statusGame, scoreName2)
    }

    override suspend fun updateStatusFinishedAndScoreName3(
        idGame: Int, statusGame: Byte, scoreName3: Short
    ): Int {
        return dao.updateStatusAndScoreName3(idGame, statusGame, scoreName3)
    }

    override suspend fun updateStatusFinishedAndScoreName4(
        idGame: Int, statusGame: Byte, scoreName4: Short
    ): Int {
        return dao.updateStatusAndScoreName4(idGame, statusGame, scoreName4)
    }

    override suspend fun updateStatusWinningPoints(
        idGame: Int, statusGame: Byte, winningPoints: Short
    ) {
        dao.updateStatusWinningPoints(idGame, statusGame, winningPoints)

    }

    override suspend fun updateOnlyStatus(idGame: Int, statusGame: Byte) {
        dao.updateOnlyStatus(idGame, statusGame)

    }

}