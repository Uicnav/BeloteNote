package com.ionvaranita.belotenote.domain.usecase.winningpoints.get

import com.ionvaranita.belotenote.domain.model.WinningPointsUi
import com.ionvaranita.belotenote.domain.repo.winningpoints.WinningPointsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class GetWinningPointsUseCase(private val repository: WinningPointsRepository) :
    UseCase<Unit, List<WinningPointsUi>> {

    override suspend fun execute(params: Unit): List<WinningPointsUi> {
        return repository.getWinningPoints().map { it.toUiModel() }
    }
}