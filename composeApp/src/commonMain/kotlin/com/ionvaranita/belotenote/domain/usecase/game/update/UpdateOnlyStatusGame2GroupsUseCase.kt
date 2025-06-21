package com.ionvaranita.belotenote.domain.usecase.game.update

import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateOnlyStatusGameParams
import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusWinningPointsGameParams
import com.ionvaranita.belotenote.domain.repo.game.Games2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class UpdateOnlyStatusGame2GroupsUseCase(private val repository: Games2GroupsRepository) :
    UseCase<UpdateOnlyStatusGameParams, Unit> {
    override suspend fun execute(params: UpdateOnlyStatusGameParams) {
         repository.updateOnlyStatus(
            idGame = params.idGame,
            gameStatus = params.statusGame,
        )
    }

}