package com.ionvaranita.belotenote.domain.usecase.game.update

import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusAndScoreGameParams
import com.ionvaranita.belotenote.domain.repo.game.Games2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class UpdateStatusScoreGame2GroupsName1UseCase(private val repository: Games2GroupsRepository) :
    UseCase<UpdateStatusAndScoreGameParams, Int> {
    override suspend fun execute(params: UpdateStatusAndScoreGameParams): Int {
        return repository.updateStatusFinishedAndScoreName1(
            idGame = params.idGame,
            statusGame = params.statusGame,
            scoreName1 = params.score
        )
    }

}

class UpdateStatusScoreGame2GroupsName2UseCase(private val repository: Games2GroupsRepository) :
    UseCase<UpdateStatusAndScoreGameParams, Int> {
    override suspend fun execute(params: UpdateStatusAndScoreGameParams): Int {
        return repository.updateStatusFinishedAndScoreName2(
            idGame = params.idGame,
            statusGame = params.statusGame,
            scoreName2 = params.score
        )
    }

}