package com.ionvaranita.belotenote.datalayer.datasource.game

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game2PDataSource
import kotlinx.coroutines.flow.Flow
import varanita.informatics.shared.database.dao.players2.Game2PDao

class Game2PDataSourceImpl(private val dao: Game2PDao): Game2PDataSource {
    override suspend fun getGame(idGame: Int): Flow<Game2PEntity> {
        return dao.getGame(idGame)
    }

    override suspend fun getGames(): Flow<List<Game2PEntity>> {
        return dao.getGames()
    }

    override suspend fun insertGame(game2PEntity: Game2PEntity) {
        dao.insert(game2PEntity)
    }

    override suspend fun deleteGame(idGame: Int) {
        dao.delete(idGame)
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

    override suspend fun updateStatusWinningPoints(idGame: Int, gameStatus: Byte, winningPoints: Short) {
        dao.updateStatusWinningPoints(idGame = idGame, statusGame = gameStatus, winningPoints)
    }

}