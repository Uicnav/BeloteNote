package com.ionvaranita.belotenote.datalayer.repo

import com.ionvaranita.belotenote.datalayer.database.entity.WinningPointsEntity
import com.ionvaranita.belotenote.domain.datasource.WinningPointsDataSource
import com.ionvaranita.belotenote.domain.repo.WinningPointsRepository
import kotlinx.coroutines.flow.Flow

class WinningPointsRepositoryImpl(private val dataSource: WinningPointsDataSource) : WinningPointsRepository {
    override suspend fun getWinningPoints(): Flow<List<WinningPointsEntity>> {
       return dataSource.getWinningPoints()
    }

}