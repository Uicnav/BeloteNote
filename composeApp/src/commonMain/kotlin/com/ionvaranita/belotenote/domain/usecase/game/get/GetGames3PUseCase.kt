package com.ionvaranita.belotenote.domain.usecase.game.get

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.domain.repo.game.Games3PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGames3PUseCase(private val repository: Games3PRepository) : UseCase<Unit, Flow<List<Game3PUi>>> {
    override suspend fun execute(params: Unit): Flow<List<Game3PUi>> =  repository.getGames().map(::games3PToUI)

}

private fun games3PToUI(listGames: List<Game3PEntity>): List<Game3PUi> {
    val result = mutableListOf<Game3PUi>()
    listGames.forEach { game->
        result.add(game.toUiModel())
    }
    return result
}