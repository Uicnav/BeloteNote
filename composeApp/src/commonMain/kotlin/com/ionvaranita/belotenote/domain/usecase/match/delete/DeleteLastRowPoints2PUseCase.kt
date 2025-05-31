package com.ionvaranita.belotenote.domain.usecase.match.delete

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import com.ionvaranita.belotenote.domain.repo.match.Points2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteLastRowPoints2PUseCase(private val repository: Points2PRepository) :
    UseCase<Points2PEntity, Unit> {
    override suspend fun execute(points: Points2PEntity) {
        return repository.delete(points)
    }
}