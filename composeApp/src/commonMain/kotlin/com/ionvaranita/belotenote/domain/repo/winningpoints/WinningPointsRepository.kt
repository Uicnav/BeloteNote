package com.ionvaranita.belotenote.domain.repo.winningpoints

import com.ionvaranita.belotenote.datalayer.database.entity.winningpoints.WinningPointsEntity
import kotlinx.coroutines.flow.Flow

interface WinningPointsRepository {
    suspend fun getWinningPoints(): Flow<List<WinningPointsEntity>>
    suspend fun insertWinningPoints(winningPointsEntity: WinningPointsEntity)
}