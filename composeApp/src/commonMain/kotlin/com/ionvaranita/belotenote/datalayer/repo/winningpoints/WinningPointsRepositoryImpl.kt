package com.ionvaranita.belotenote.datalayer.repo.winningpoints

import com.ionvaranita.belotenote.datalayer.database.entity.winningpoints.WinningPointsEntity
import com.ionvaranita.belotenote.domain.datasource.winningpoints.WinningPointsDataSource
import com.ionvaranita.belotenote.domain.repo.winningpoints.WinningPointsRepository

class WinningPointsRepositoryImpl(private val datasource: WinningPointsDataSource) :
    WinningPointsRepository {
    override suspend fun getWinningPoints(): List<WinningPointsEntity> {
        return datasource.getWinningPoints()
    }

    override suspend fun insertWinningPoints(winningPointsEntity: WinningPointsEntity) {
        datasource.insertWinningPoints(winningPointsEntity)
    }
}