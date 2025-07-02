package com.ionvaranita.belotenote.domain.usecase.game.get

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.model.Game4PUi
import com.ionvaranita.belotenote.domain.repo.game.Games4PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGame4PUseCase(private val repository: Games4PRepository) : UseCase<Int, Flow<Game4PUi>> {
    override suspend fun execute(params: Int): Flow<Game4PUi> =
        repository.getGame(idGame = params).map(
            Game4PEntity::toUiModel
        )

}