package com.ionvaranita.belotenote.domain.usecase.match.insert

import com.ionvaranita.belotenote.domain.model.Points4PUi
import com.ionvaranita.belotenote.domain.repo.match.Points4PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertPoints4PUseCase(private val repository: Points4PRepository) :
    UseCase<Points4PUi, Long> {
    override suspend fun execute(params: Points4PUi): Long {
        return repository.insert(params)
    }
}