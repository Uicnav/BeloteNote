package com.ionvaranita.belotenote.domain.usecase.game.insert

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.repo.game.Games4PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertGame4PUseCase(private val repository: Games4PRepository) : UseCase<Game4PEntity, Int> {
    override suspend fun execute(game: Game4PEntity): Int = repository.insertGame(game = game)

}