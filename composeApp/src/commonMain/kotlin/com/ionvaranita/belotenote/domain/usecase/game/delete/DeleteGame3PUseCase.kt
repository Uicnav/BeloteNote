package com.ionvaranita.belotenote.domain.usecase.game.delete

import com.ionvaranita.belotenote.domain.repo.game.Games3PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteGame3PUseCase(private val repository: Games3PRepository) : UseCase<Int, Unit> {
    override suspend fun execute(params: Int): Unit = repository.deleteGame(idGame = params)
}