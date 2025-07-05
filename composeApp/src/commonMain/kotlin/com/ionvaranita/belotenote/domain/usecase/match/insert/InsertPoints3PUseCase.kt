package com.ionvaranita.belotenote.domain.usecase.match.insert

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity
import com.ionvaranita.belotenote.domain.model.Points3PUi
import com.ionvaranita.belotenote.domain.repo.match.Points3PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertPoints3PUseCase(private val repository: Points3PRepository) :
    UseCase<Points3PUi, Long> {
    override suspend fun execute(params: Points3PUi): Long {
        return repository.insert(params)
    }
}