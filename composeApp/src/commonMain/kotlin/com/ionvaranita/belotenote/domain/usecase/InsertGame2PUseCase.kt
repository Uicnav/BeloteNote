package com.ionvaranita.belotenote.domain.usecase

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.repo.Games2PRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InsertGame2PUseCase(private val repository: Games2PRepository) : UseCase<Game2PEntity, Unit> {
    override suspend fun execute(game2PEntity: Game2PEntity): Unit =  repository.insetGame(game2PEntity = game2PEntity)

}