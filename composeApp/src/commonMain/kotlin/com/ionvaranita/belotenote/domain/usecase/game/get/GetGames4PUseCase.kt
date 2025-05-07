package com.ionvaranita.belotenote.domain.usecase.game.get

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.model.Game4PUi
import com.ionvaranita.belotenote.domain.repo.game.Games4PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGames4PUseCase(private val repository: Games4PRepository) : UseCase<Unit, Flow<List<Game4PUi>>> {
    override suspend fun execute(params: Unit): Flow<List<Game4PUi>> = repository.getGames().map(::games4PToUI)
    private fun games4PToUI(listGames: List<Game4PEntity>): List<Game4PUi> {
        val result = mutableListOf<Game4PUi>()
        listGames.forEach { game ->
            result.add(game.toUiModel())
        }
        return result
    }
}