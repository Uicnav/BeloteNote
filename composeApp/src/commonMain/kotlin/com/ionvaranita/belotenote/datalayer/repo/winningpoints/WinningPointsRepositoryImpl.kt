package com.ionvaranita.belotenote.datalayer.repo.winningpoints

import com.ionvaranita.belotenote.datalayer.database.entity.winningpoints.WinningPointsEntity
import com.ionvaranita.belotenote.domain.datasource.winningpoints.WinningPointsDataSource
import com.ionvaranita.belotenote.domain.repo.winningpoints.WinningPointsRepository
import kotlinx.coroutines.flow.Flow

class WinningPointsRepositoryImpl(private val datasource: WinningPointsDataSource) :
    WinningPointsRepository {
    override suspend fun getWinningPoints(): Flow<List<WinningPointsEntity>> {
        return datasource.getWinningPoints()
    }

    override suspend fun insertWinningPoints(winningPointsEntity: WinningPointsEntity) {
        datasource.insertWinningPoints(winningPointsEntity)
    }
}