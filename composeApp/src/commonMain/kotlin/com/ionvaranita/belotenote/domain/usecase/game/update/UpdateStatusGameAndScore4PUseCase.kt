package com.ionvaranita.belotenote.domain.usecase.game.update

import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusAndScoreGameParams
import com.ionvaranita.belotenote.domain.repo.game.Games4PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class UpdateStatusScoreName1Game4PUseCase(private val repository: Games4PRepository) :
    UseCase<UpdateStatusAndScoreGameParams, Int> {
    override suspend fun execute(params: UpdateStatusAndScoreGameParams): Int {
        return repository.updateStatusFinishedAndScoreName1(
            idGame = params.idGame, statusGame = params.statusGame, scoreName1 = params.score
        )
    }

}

class UpdateStatusScoreName2Game4PUseCase(private val repository: Games4PRepository) :
    UseCase<UpdateStatusAndScoreGameParams, Int> {
    override suspend fun execute(params: UpdateStatusAndScoreGameParams): Int {
        return repository.updateStatusFinishedAndScoreName2(
            idGame = params.idGame, statusGame = params.statusGame, scoreName2 = params.score
        )
    }

}

class UpdateStatusScoreName3Game4PUseCase(private val repository: Games4PRepository) :
    UseCase<UpdateStatusAndScoreGameParams, Int> {
    override suspend fun execute(params: UpdateStatusAndScoreGameParams): Int {
        return repository.updateStatusFinishedAndScoreName3(
            idGame = params.idGame, statusGame = params.statusGame, scoreName3 = params.score
        )
    }

}

class UpdateStatusScoreName4Game4PUseCase(private val repository: Games4PRepository) :
    UseCase<UpdateStatusAndScoreGameParams, Int> {
    override suspend fun execute(params: UpdateStatusAndScoreGameParams): Int {
        return repository.updateStatusFinishedAndScoreName4(
            idGame = params.idGame, statusGame = params.statusGame, scoreName4 = params.score
        )
    }

}