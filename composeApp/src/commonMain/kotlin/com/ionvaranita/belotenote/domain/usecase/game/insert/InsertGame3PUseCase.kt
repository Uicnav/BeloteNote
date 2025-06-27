package com.ionvaranita.belotenote.domain.usecase.game.insert

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.domain.repo.game.Games3PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertGame3PUseCase(private val repository: Games3PRepository) : UseCase<Game3PEntity, Int> {
    override suspend fun execute(game: Game3PEntity): Int =  repository.insetGame(game = game)

}