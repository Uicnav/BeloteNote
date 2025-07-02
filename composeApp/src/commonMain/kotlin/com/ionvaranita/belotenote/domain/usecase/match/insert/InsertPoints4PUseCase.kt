package com.ionvaranita.belotenote.domain.usecase.match.insert

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Points4PEntity
import com.ionvaranita.belotenote.domain.repo.match.Points4PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertPoints4PUseCase(private val repository: Points4PRepository) :
    UseCase<Points4PEntity, Long> {
    override suspend fun execute(points: Points4PEntity): Long {
        return repository.insert(points)
    }
}