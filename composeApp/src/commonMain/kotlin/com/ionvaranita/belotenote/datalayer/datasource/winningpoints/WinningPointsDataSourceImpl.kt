package com.ionvaranita.belotenote.datalayer.datasource.winningpoints

import com.ionvaranita.belotenote.datalayer.database.dao.winningpoints.WinningPointsDao
import com.ionvaranita.belotenote.datalayer.database.entity.winningpoints.WinningPointsEntity
import com.ionvaranita.belotenote.domain.datasource.winningpoints.WinningPointsDataSource
import com.ionvaranita.belotenote.domain.repo.winningpoints.WinningPointsRepository

class WinningPointsDataSourceImpl(private val dao: WinningPointsDao) :
    WinningPointsDataSource {
    override suspend fun getWinningPoints(): List<WinningPointsEntity> {
        return dao.getWinningPoints()
    }

    override suspend fun insertWinningPoints(winningPointsEntity: WinningPointsEntity) {
        dao.insert(winningPointsEntity)
    }
}