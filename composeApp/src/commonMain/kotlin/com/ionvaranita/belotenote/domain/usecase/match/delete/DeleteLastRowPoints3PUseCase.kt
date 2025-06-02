package com.ionvaranita.belotenote.domain.usecase.match.delete

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity
import com.ionvaranita.belotenote.domain.repo.match.Points3PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteLastRowPoints3PUseCase(private val repository: Points3PRepository) :
    UseCase<Points3PEntity, Unit> {
    override suspend fun execute(points: Points3PEntity) {
        return repository.delete(points)
    }
}