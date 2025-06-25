package com.ionvaranita.belotenote.domain.usecase.game.update

import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusWinningPointsGameParams
import com.ionvaranita.belotenote.domain.repo.game.Games2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class UpdateStatusWinningPointsGame2PUseCase(private val repository: Games2PRepository) :
    UseCase<UpdateStatusWinningPointsGameParams, Unit> {
    override suspend fun execute(params: UpdateStatusWinningPointsGameParams) {
         repository.updateStatusWinningPoints(
            idGame = params.idGame,
            statusGame = params.statusGame,
             winningPoints = params.winningPoints
        )
    }

}