package com.ionvaranita.belotenote.domain.usecase.match.get

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.repo.match.Points2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPoints2GroupsUseCase(private val repository: Points2GroupsRepository) :
    UseCase<Int, Flow<List<Points2GroupsUi>>> {
    override suspend fun execute(params: Int): Flow<List<Points2GroupsUi>> =
        repository.getPoints(idGame = params).map(::points2GroupsToUI)

    private fun points2GroupsToUI(listGames: List<Points2GroupsEntity>): List<Points2GroupsUi> {
        val result = mutableListOf<Points2GroupsUi>()
        listGames.forEach { game ->
            result.add(game.toUiModel())
        }
        return result
    }
}