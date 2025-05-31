package com.ionvaranita.belotenote.domain.usecase.game.get

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.domain.repo.game.Games2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGames2GroupsUseCase(private val repository: Games2GroupsRepository) : UseCase<Unit, Flow<List<Game2GroupsUi>>> {
    override suspend fun execute(params: Unit): Flow<List<Game2GroupsUi>> = repository.getGames().map(::games2GroupsToUI)

    private fun games2GroupsToUI(listGames: List<Game2GroupsEntity>): List<Game2GroupsUi> {
        val result = mutableListOf<Game2GroupsUi>()
        listGames.forEach { game ->
            result.add(game.toUiModel())
        }
        return result
    }

}