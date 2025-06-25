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

    override suspend fun updateStatusAndScoreName1(
        idGame: Int,
        statusGame: Byte,
        scoreName1: Short
    ): Int {
        return dao.updateStatusAndScoreName1(idGame, statusGame, scoreName1)
    }

    override suspend fun updateStatusAndScoreName2(
        idGame: Int,
        statusGame: Byte,
        scoreName2: Short
    ): Int {
        return dao.updateStatusAndScoreName2(idGame, statusGame, scoreName2)
    }

    override suspend fun updateStatusAndScoreName3(
        idGame: Int,
        statusGame: Byte,
        scoreName3: Short
    ): Int {
        return dao.updateStatusAndScoreName3(idGame, statusGame, scoreName3)
    }

    override suspend fun updateStatusWinningPoints(
        idGame: Int,
        statusGame: Byte,
        winningPoints: Short
    ) {
        dao.updateStatusWinningPoints(idGame, statusGame, winningPoints)
    }

    override suspend fun updateOnlyStatus(idGame: Int, statusGame: Byte) {
        dao.updateOnlyStatus(idGame, statusGame)
    }

}