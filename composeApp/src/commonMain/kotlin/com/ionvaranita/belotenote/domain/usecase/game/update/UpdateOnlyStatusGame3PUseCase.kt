package com.ionvaranita.belotenote.domain.usecase.game.update

import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateOnlyStatusGameParams
import com.ionvaranita.belotenote.domain.repo.game.Games2PRepository
import com.ionvaranita.belotenote.domain.repo.game.Games3PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class UpdateOnlyStatusGame3PUseCase(private val repository: Games3PRepository) :
    UseCase<UpdateOnlyStatusGameParams, Unit> {
    override suspend fun execute(params: UpdateOnlyStatusGameParams) {
         repository.updateOnlyStatus(
            idGame = params.idGame,
            statusGame = params.statusGame,
        )
    }

}