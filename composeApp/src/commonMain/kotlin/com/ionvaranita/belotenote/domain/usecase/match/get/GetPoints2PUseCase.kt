package com.ionvaranita.belotenote.domain.usecase.match.get

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import com.ionvaranita.belotenote.domain.model.Points2PUi
import com.ionvaranita.belotenote.domain.repo.match.Points2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPoints2PUseCase(private val repository: Points2PRepository) :
    UseCase<Int, Flow<List<Points2PUi>>> {
    override suspend fun execute(params: Int): Flow<List<Points2PUi>> =
        repository.getPoints(idGame = params).map(::points2GroupsToUI)

    private fun points2GroupsToUI(listGames: List<Points2PEntity>): List<Points2PUi> {
        val result = mutableListOf<Points2PUi>()
        listGames.forEach { game ->
            result.add(game.toUiModel())
        }
        return result
    }
}