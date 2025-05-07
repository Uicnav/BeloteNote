package com.ionvaranita.belotenote.domain.usecase.game.delete

import com.ionvaranita.belotenote.domain.repo.game.Games2GroupsRepository
import com.ionvaranita.belotenote.domain.repo.game.Games4PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteGameGroupsUseCase(private val repository: Games2GroupsRepository) : UseCase<Int, Unit> {
    override suspend fun execute(params: Int): Unit = repository.deleteGame(idGame = params)
}