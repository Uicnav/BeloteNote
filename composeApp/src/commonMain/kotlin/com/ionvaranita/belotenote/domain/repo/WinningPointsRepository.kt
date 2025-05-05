package com.ionvaranita.belotenote.domain.repo

import com.ionvaranita.belotenote.datalayer.database.entity.WinningPointsEntity
import kotlinx.coroutines.flow.Flow

interface WinningPointsRepository {
    suspend fun getWinningPoints(): Flow<List<WinningPointsEntity>>
}