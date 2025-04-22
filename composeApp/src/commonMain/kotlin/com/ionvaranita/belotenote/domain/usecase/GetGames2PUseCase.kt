package com.ionvaranita.belotenote.domain.usecase

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.repo.Games2PRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGames2PUseCase (private val repository: Games2PRepository) : UseCase<Unit, Flow<List<Game2PUi>>> {
    override suspend fun execute(params: Unit): Flow<List<Game2PUi>> =  repository.getGames().map(::games2PToUI)

}

private fun games2PToUI(listGames: List<Game2PEntity>): List<Game2PUi> {
    val result = mutableListOf<Game2PUi>()
    listGames.forEach { game->
        result.add(Game2PUi(idGame = game.idGame, statusGame = game.statusGame, winnerPoints = game.winnerPoints, name1 = game.name1, name2 = game.name2))
    }
    return result
}