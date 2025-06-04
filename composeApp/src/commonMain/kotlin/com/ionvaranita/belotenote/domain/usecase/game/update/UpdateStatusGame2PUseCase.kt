package com.ionvaranita.belotenote.domain.usecase.game.update

import com.ionvaranita.belotenote.datalayer.database.entity.players2.UpdateStatusAndScoreGame2P
import com.ionvaranita.belotenote.domain.repo.game.Games2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class UpdateStatusScoreName1Game2PUseCase(private val repository: Games2PRepository) :
    UseCase<UpdateStatusAndScoreGame2P, Int> {
    override suspend fun execute(params: UpdateStatusAndScoreGame2P): Int {
        return repository.updateStatusAndScoreName1(
            idGame = params.idGame,
            statusGame = params.statusGame,
            scoreName1 = params.score
        )
    }

}

class UpdateStatusScoreName2Game2PUseCase(private val repository: Games2PRepository) :
    UseCase<UpdateStatusAndScoreGame2P, Int> {
    override suspend fun execute(params: UpdateStatusAndScoreGame2P): Int {
        return repository.updateStatusAndScoreName2(
            idGame = params.idGame,
            statusGame = params.statusGame,
            scoreName2 = params.score
        )
    }

}