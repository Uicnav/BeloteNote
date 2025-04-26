package com.ionvaranita.belotenote.domain.usecase

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.domain.repo.Games3PRepository

class InsertGame3PUseCase(private val repository: Games3PRepository) : UseCase<Game3PEntity, Unit> {
    override suspend fun execute(game: Game3PEntity): Unit =  repository.insetGame(game = game)

}