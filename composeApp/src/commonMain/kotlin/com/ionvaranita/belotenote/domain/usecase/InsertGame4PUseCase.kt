package com.ionvaranita.belotenote.domain.usecase

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.repo.Games4PRepository

class InsertGame4PUseCase(private val repository: Games4PRepository) : UseCase<Game4PEntity, Unit> {
    override suspend fun execute(game: Game4PEntity): Unit = repository.insetGame(game = game)

}