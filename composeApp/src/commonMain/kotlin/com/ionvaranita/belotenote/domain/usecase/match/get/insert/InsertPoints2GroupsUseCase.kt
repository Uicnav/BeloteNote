package com.ionvaranita.belotenote.domain.usecase.match.get.insert

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.domain.repo.match.Points2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertPoints2GroupsUseCase(private val repository: Points2GroupsRepository) :
    UseCase<Points2GroupsEntity, Unit> {
    override suspend fun execute(points: Points2GroupsEntity) {
        repository.insert(points)
    }
}