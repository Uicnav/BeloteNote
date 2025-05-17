package com.ionvaranita.belotenote.datalayer.repo.game

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.datasource.game.Game2PDataSource
import com.ionvaranita.belotenote.domain.repo.game.Games2PRepository
import kotlinx.coroutines.flow.Flow

class Games2PRepositoryImpl(private val games2P2DataSource: Game2PDataSource): Games2PRepository {
    override suspend fun getGame(idGame: Int): Flow<Game2PEntity> {
        return games2P2DataSource.getGame(idGame)
    }

    override suspend fun getGames(): Flow<List<Game2PEntity>> {
        return games2P2DataSource.getGames()
    }

    override suspend fun insetGame(game2PEntity: Game2PEntity) {
        return games2P2DataSource.insertGame(game2PEntity)
    }

    override suspend fun deleteGame(idGame: Int) {
        games2P2DataSource.deleteGame(idGame)
    }

}