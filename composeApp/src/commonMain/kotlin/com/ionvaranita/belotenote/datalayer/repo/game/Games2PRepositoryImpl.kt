package com.ionvaranita.belotenote.datalayer.repo.game

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game2PDataSource
import com.ionvaranita.belotenote.domain.repo.game.Games2PRepository
import kotlinx.coroutines.flow.Flow

class Games2PRepositoryImpl(private val dataSource: Game2PDataSource): Games2PRepository {
    override suspend fun getGame(idGame: Int): Flow<Game2PEntity> {
        return dataSource.getGame(idGame)
    }

    override suspend fun getGames(): Flow<List<Game2PEntity>> {
        return dataSource.getGames()
    }

    override suspend fun insetGame(game2PEntity: Game2PEntity) {
        return dataSource.insertGame(game2PEntity)
    }

    override suspend fun deleteGame(idGame: Int) {
        dataSource.deleteGame(idGame)
    }

    override suspend fun updateStatusAndScoreName1(
        idGame: Int,
        statusGame: Byte,
        scoreName1: Short
    ): Int {
        return dataSource.updateStatusAndScoreName1(idGame, statusGame, scoreName1)
    }

    override suspend fun updateStatusAndScoreName2(
        idGame: Int,
        statusGame: Byte,
        scoreName2: Short
    ): Int {
        return dataSource.updateStatusAndScoreName2(idGame, statusGame, scoreName2)

    }

}