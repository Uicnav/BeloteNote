package com.ionvaranita.belotenote.domain.datasource.winningpoints

import com.ionvaranita.belotenote.datalayer.database.entity.winningpoints.WinningPointsEntity
import kotlinx.coroutines.flow.Flow

interface WinningPointsDataSource {
    suspend fun getWinningPoints(): Flow<List<WinningPointsEntity>>
    suspend fun insertWinningPoints(winningPointsEntity: WinningPointsEntity)
}