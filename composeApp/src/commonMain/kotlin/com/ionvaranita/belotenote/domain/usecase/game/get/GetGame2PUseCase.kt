package com.ionvaranita.belotenote.domain.usecase.game.get

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.repo.game.Games2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGame2PUseCase(private val repository: Games2PRepository) : UseCase<Int, Flow<Game2PUi>> {
    override suspend fun execute(params: Int): Flow<Game2PUi> =  repository.getGame(idGame = params).map(Game2PEntity::toUiModel)

}