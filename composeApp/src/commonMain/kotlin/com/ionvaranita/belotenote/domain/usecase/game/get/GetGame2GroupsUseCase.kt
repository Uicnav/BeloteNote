package com.ionvaranita.belotenote.domain.usecase.game.get

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.repo.game.Games2GroupsRepository
import com.ionvaranita.belotenote.domain.repo.game.Games2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGame2GroupsUseCase(private val repository: Games2GroupsRepository) : UseCase<Int, Flow<Game2GroupsUi>> {
    override suspend fun execute(params: Int): Flow<Game2GroupsUi> =  repository.getGame(idGame = params).map(Game2GroupsEntity::toUiModel)

}