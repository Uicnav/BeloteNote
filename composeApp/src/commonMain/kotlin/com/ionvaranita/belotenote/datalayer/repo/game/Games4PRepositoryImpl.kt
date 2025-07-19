package com.ionvaranita.belotenote.datalayer.repo.game

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game4PDataSource
import com.ionvaranita.belotenote.domain.repo.game.Games4PRepository
import kotlinx.coroutines.flow.Flow

class Games4PRepositoryImpl(private val datasource: Game4PDataSource) : Games4PRepository {
    override suspend fun getGames(): Flow<List<Game4PEntity>> {
        return datasource.getGames()
    }

    override suspend fun insertGame(game: Game4PEntity): Int {
       return datasource.insertGame(game)
    }

    override suspend fun deleteGame(idGame: Int) {
        datasource.deleteGame(idGame)
    }

    override suspend fun getGame(idGame: Int): Flow<Game4PEntity> {
        return datasource.getGame(idGame)
    }

    override suspend fun updateStatusFinishedAndScoreName1(
        idGame: Int,
        statusGame: Byte,
        scoreName1: Short
    ): Int {
        return datasource.updateStatusFinishedAndScoreName1(idGame, statusGame, scoreName1)
    }

    override suspend fun updateStatusFinishedAndScoreName2(
        idGame: Int,
        statusGame: Byte,
        scoreName2: Short
    ): Int {
        return datasource.updateStatusFinishedAndScoreName2(idGame, statusGame, scoreName2)

    }

    override suspend fun updateStatusFinishedAndScoreName3(
        idGame: Int,
        statusGame: Byte,
        scoreName3: Short
    ): Int {
        return datasource.updateStatusFinishedAndScoreName3(idGame, statusGame, scoreName3)

    }

    override suspend fun updateStatusFinishedAndScoreName4(
        idGame: Int,
        statusGame: Byte,
        scoreName4: Short
    ): Int {
        return datasource.updateStatusFinishedAndScoreName4(idGame, statusGame, scoreName4)
    }

    override suspend fun updateStatusWinningPoints(
        idGame: Int,
        statusGame: Byte,
        winningPoints: Short
    ) {
        datasource.updateStatusWinningPoints(idGame, statusGame, winningPoints)
    }

    override suspend fun updateOnlyStatus(idGame: Int, statusGame: Byte) {
        datasource.updateOnlyStatus(idGame, statusGame)
    }

}