package com.ionvaranita.belotenote.domain.datasource.winningpoints

import com.ionvaranita.belotenote.datalayer.database.entity.winningpoints.WinningPointsEntity

interface WinningPointsDataSource {
    suspend fun getWinningPoints(): List<WinningPointsEntity>
    suspend fun insertWinningPoints(winningPointsEntity: WinningPointsEntity)
}