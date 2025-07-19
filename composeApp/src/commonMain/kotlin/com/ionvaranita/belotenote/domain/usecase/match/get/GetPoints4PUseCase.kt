package com.ionvaranita.belotenote.domain.usecase.match.get

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Points4PEntity
import com.ionvaranita.belotenote.domain.model.Points4PUi
import com.ionvaranita.belotenote.domain.repo.match.Points4PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPoints4PUseCase(private val repository: Points4PRepository) :
    UseCase<Int, Flow<List<Points4PUi>>> {
    override suspend fun execute(params: Int): Flow<List<Points4PUi>> =
        repository.getPoints(idGame = params).map(::points4PToUI)

    private fun points4PToUI(list: List<Points4PEntity>): List<Points4PUi> {
        val result = mutableListOf<Points4PUi>()
        list.forEach { point ->
            result.add(point.toUiModel())
        }
        return result
    }
}