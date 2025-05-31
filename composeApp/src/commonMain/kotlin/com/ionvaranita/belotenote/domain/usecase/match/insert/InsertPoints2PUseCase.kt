package com.ionvaranita.belotenote.domain.usecase.match.insert

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import com.ionvaranita.belotenote.domain.repo.match.Points2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertPoints2PUseCase(private val repository: Points2PRepository) :
    UseCase<Points2PEntity, Long> {
    override suspend fun execute(points: Points2PEntity): Long {
        return repository.insert(points)
    }
}