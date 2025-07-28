package com.ionvaranita.belotenote.domain.repo.winningpoints

import com.ionvaranita.belotenote.datalayer.database.entity.winningpoints.WinningPointsEntity

interface WinningPointsRepository {
    suspend fun getWinningPoints(): List<WinningPointsEntity>
    suspend fun insertWinningPoints(winningPointsEntity: WinningPointsEntity)
}