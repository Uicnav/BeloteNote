package com.ionvaranita.belotenote.datalayer.repo.game

import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game2GroupsDataSource
import com.ionvaranita.belotenote.domain.repo.game.Games2GroupsRepository
import kotlinx.coroutines.flow.Flow

class Games2GroupsRepositoryImpl(private val datasource: Game2GroupsDataSource) : Games2GroupsRepository {
    override suspend fun getGame(idGame: Int): Flow<Game2GroupsEntity> {
        return datasource.getGame(idGame)
    }

    override suspend fun getGames(): Flow<List<Game2GroupsEntity>> {
        return datasource.getGames()
    }

    override suspend fun insetGame(game: Game2GroupsEntity) {
        datasource.insertGame(game)
    }

    override suspend fun deleteGame(idGame: Int) {
        datasource.deleteGame(idGame)
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

    override suspend fun updateOnlyStatus(idGame: Int, gameStatus: Byte) {
        datasource.updateOnlyStatus(idGame, gameStatus)
    }

}