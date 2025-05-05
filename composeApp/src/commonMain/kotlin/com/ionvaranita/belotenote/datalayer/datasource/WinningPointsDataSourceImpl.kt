package com.ionvaranita.belotenote.datalayer.datasource

import com.ionvaranita.belotenote.datalayer.database.dao.WinningPointsDao
import com.ionvaranita.belotenote.datalayer.database.entity.WinningPointsEntity
import com.ionvaranita.belotenote.domain.datasource.WinningPointsDataSource
import kotlinx.coroutines.flow.Flow

class WinningPointsDataSourceImpl(private val dao: WinningPointsDao) : WinningPointsDataSource {
    override suspend fun getWinningPoints(): Flow<List<WinningPointsEntity>> {
        return dao.winnerPoints()
    }

}