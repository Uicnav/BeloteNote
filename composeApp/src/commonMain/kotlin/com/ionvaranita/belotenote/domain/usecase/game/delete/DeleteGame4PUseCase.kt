package com.ionvaranita.belotenote.domain.usecase.game.delete

import com.ionvaranita.belotenote.domain.repo.game.Games4PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteGame4PUseCase(private val repository: Games4PRepository) : UseCase<Int, Unit> {
    override suspend fun execute(params: Int): Unit = repository.deleteGame(idGame = params)
}