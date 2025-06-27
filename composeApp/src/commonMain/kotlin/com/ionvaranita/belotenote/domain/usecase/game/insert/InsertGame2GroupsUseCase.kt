package com.ionvaranita.belotenote.domain.usecase.game.insert

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.domain.repo.game.Games2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertGame2GroupsUseCase(private val repository: Games2GroupsRepository) : UseCase<Game2GroupsEntity, Int> {
    override suspend fun execute(game: Game2GroupsEntity): Int = repository.insetGame(game = game)
}