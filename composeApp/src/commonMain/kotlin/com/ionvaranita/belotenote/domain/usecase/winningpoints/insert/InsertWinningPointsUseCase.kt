package com.ionvaranita.belotenote.domain.usecase.winningpoints.insert

import com.ionvaranita.belotenote.domain.model.WinningPointsUi
import com.ionvaranita.belotenote.domain.repo.winningpoints.WinningPointsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertWinningPointsUseCase(private val repository: WinningPointsRepository): UseCase<WinningPointsUi, Unit> {
    override suspend fun execute(params: WinningPointsUi) {
        repository.insertWinningPoints(params.toDataClass())
    }
}