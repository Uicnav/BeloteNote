package com.ionvaranita.belotenote.domain.usecase.game.insert

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.repo.game.Games2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertGame2PUseCase(private val repository: Games2PRepository) : UseCase<Game2PEntity, Unit> {
    override suspend fun execute(game2PEntity: Game2PEntity) =  repository.insetGame(game2PEntity = game2PEntity)

}