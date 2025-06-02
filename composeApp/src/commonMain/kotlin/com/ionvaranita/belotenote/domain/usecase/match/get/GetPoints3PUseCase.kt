package com.ionvaranita.belotenote.domain.usecase.match.get

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity
import com.ionvaranita.belotenote.domain.model.Points3PUi
import com.ionvaranita.belotenote.domain.repo.match.Points3PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPoints3PUseCase(private val repository: Points3PRepository) :
    UseCase<Int, Flow<List<Points3PUi>>> {
    override suspend fun execute(params: Int): Flow<List<Points3PUi>> =
        repository.getPoints(idGame = params).map(::points2GroupsToUI)

    private fun points2GroupsToUI(list: List<Points3PEntity>): List<Points3PUi> {
        val result = mutableListOf<Points3PUi>()
        list.forEach { point ->
            result.add(point.toUiModel())
        }
        return result
    }
}