package com.ionvaranita.belotenote.domain.usecase

import com.ionvaranita.belotenote.datalayer.database.entity.WinningPointsEntity
import com.ionvaranita.belotenote.domain.model.WinningPointsUi
import com.ionvaranita.belotenote.domain.repo.WinningPointsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWinningPointsUseCase(private val repository: WinningPointsRepository) : UseCase<Unit, Flow<List<WinningPointsUi>>> {
    override suspend fun execute(params: Unit): Flow<List<WinningPointsUi>> = repository.getWinningPoints().map(::winningPointsToUI)
    private fun winningPointsToUI(listGames: List<WinningPointsEntity>): List<WinningPointsUi> {
        val result = mutableListOf<WinningPointsUi>()
        listGames.forEach { game ->
            result.add(game.toUiModel())
        }
        return result
    }
}