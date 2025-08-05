package com.ionvaranita.belotenote.domain.usecase.winningpoints.get

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.winningpoints.WinningPointsEntity
import com.ionvaranita.belotenote.domain.model.Points2PUi
import com.ionvaranita.belotenote.domain.model.WinningPointsUi
import com.ionvaranita.belotenote.domain.repo.winningpoints.WinningPointsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWinningPointsUseCase(private val repository: WinningPointsRepository) :
    UseCase<Unit, Flow<List<WinningPointsUi>>> {

    override suspend fun execute(params: Unit): Flow<List<WinningPointsUi>> {
        return repository.getWinningPoints().map(::winningPointsToUI)
    }

    private fun winningPointsToUI(listGames: List<WinningPointsEntity>): List<WinningPointsUi> {
        val result = mutableListOf<WinningPointsUi>()
        listGames.forEach { game ->
            result.add(game.toUiModel())
        }
        return result
    }
}