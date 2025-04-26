package com.ionvaranita.belotenote.domain.usecase

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.domain.repo.Games2GroupsRepository
import com.ionvaranita.belotenote.domain.repo.Games4PRepository

class InsertGame2GroupsUseCase(private val repository: Games2GroupsRepository) : UseCase<Game2GroupsEntity, Unit> {
    override suspend fun execute(game: Game2GroupsEntity): Unit = repository.insetGame(game = game)
}