package com.ionvaranita.belotenote.datalayer.repo.game

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game3PDataSource
import com.ionvaranita.belotenote.domain.repo.game.Games3PRepository
import kotlinx.coroutines.flow.Flow

class Games3PRepositoryImpl(private val datasource: Game3PDataSource) : Games3PRepository {
    override suspend fun getGames(): Flow<List<Game3PEntity>> {
        return datasource.getGames()
    }

    override suspend fun getGame(idGame: Int): Flow<Game3PEntity> {
        return datasource.getGame(idGame)
    }

    override suspend fun insetGame(game: Game3PEntity): Int {
        return datasource.insertGame(game)
    }

    override suspend fun deleteGame(idGame: Int) {
        datasource.deleteGame(idGame)
    }

    override suspend fun updateStatusFinishedAndScoreName1(
        idGame: Int,
        statusGame: Byte,
        scoreName1: Short
    ): Int {
        return datasource.updateStatusAndScoreName1(idGame, statusGame, scoreName1)
    }

    override suspend fun updateStatusFinishedAndScoreName2(
        idGame: Int,
        statusGame: Byte,
        scoreName2: Short
    ): Int {
        return datasource.updateStatusAndScoreName2(idGame, statusGame, scoreName2)

    }

    override suspend fun updateStatusFinishedAndScoreName3(
        idGame: Int,
        statusGame: Byte,
        scoreName3: Short
    ): Int {
        return datasource.updateStatusAndScoreName3(idGame, statusGame, scoreName3)

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