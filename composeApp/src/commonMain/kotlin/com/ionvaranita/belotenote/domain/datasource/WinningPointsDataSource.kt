package com.ionvaranita.belotenote.domain.datasource

import com.ionvaranita.belotenote.datalayer.database.entity.WinningPointsEntity
import kotlinx.coroutines.flow.Flow

interface WinningPointsDataSource {
    suspend fun getWinningPoints(): Flow<List<WinningPointsEntity>>
}