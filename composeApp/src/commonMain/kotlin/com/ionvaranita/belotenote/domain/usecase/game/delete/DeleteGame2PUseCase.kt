package com.ionvaranita.belotenote.domain.usecase.game.delete

import com.ionvaranita.belotenote.domain.repo.game.Games2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteGame2PUseCase(private val repository: Games2PRepository) : UseCase<Int, Unit> {
    override suspend fun execute(params: Int): Unit = repository.deleteGame(idGame = params)
}