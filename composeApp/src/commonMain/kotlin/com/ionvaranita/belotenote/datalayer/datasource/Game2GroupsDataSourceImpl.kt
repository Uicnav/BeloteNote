package com.ionvaranita.belotenote.datalayer.datasource

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.datasource.Game2GroupsDataSource
import com.ionvaranita.belotenote.domain.datasource.Game4PDataSource
import kotlinx.coroutines.flow.Flow
import varanita.informatics.shared.database.dao.groups2.Game2GroupsDao
import varanita.informatics.shared.database.dao.players4.Game4PDao

class Game2GroupsDataSourceImpl(private val game2GroupsDao: Game2GroupsDao) : Game2GroupsDataSource {
    override suspend fun getGames(): Flow<List<Game2GroupsEntity>> {
        return game2GroupsDao.getGames()
    }

    override suspend fun insertGame(game: Game2GroupsEntity) {
        game2GroupsDao.insert(game)
    }

}