package com.ionvaranita.belotenote.domain.usecase.game.get

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.domain.repo.game.Games3PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGame3PUseCase(private val repository: Games3PRepository) : UseCase<Int, Flow<Game3PUi>> {
    override suspend fun execute(params: Int): Flow<Game3PUi> =
        repository.getGame(idGame = params).map(
            Game3PEntity::toUiModel
        )

}