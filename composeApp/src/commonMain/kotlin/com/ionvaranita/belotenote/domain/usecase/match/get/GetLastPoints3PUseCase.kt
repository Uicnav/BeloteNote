package com.ionvaranita.belotenote.domain.usecase.match.get

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity
import com.ionvaranita.belotenote.domain.repo.match.Points3PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class GetLastPoints3PUseCase(private val repository: Points3PRepository) :
    UseCase<Int, Points3PEntity?> {
    override suspend fun execute(params: Int): Points3PEntity? {
        return repository.getLastPoints(idGame = params)
    }
}