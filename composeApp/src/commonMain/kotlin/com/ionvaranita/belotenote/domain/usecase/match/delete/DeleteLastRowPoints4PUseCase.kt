package com.ionvaranita.belotenote.domain.usecase.match.delete

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Points4PEntity
import com.ionvaranita.belotenote.domain.repo.match.Points4PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteLastRowPoints4PUseCase(private val repository: Points4PRepository) :
    UseCase<Points4PEntity, Unit> {
    override suspend fun execute(points: Points4PEntity) {
        return repository.delete(points)
    }
}