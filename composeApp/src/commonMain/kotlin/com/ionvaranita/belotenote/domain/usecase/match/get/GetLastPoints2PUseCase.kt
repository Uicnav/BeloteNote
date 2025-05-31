package com.ionvaranita.belotenote.domain.usecase.match.get

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import com.ionvaranita.belotenote.domain.repo.match.Points2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class GetLastPoints2PUseCase(private val repository: Points2PRepository) :
    UseCase<Int, Points2PEntity?> {
    override suspend fun execute(params: Int): Points2PEntity? {
        return repository.getLastPoints(idGame = params)
    }
}