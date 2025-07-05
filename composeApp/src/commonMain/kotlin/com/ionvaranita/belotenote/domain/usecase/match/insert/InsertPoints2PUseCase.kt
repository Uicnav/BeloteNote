package com.ionvaranita.belotenote.domain.usecase.match.insert

import com.ionvaranita.belotenote.domain.model.Points2PUi
import com.ionvaranita.belotenote.domain.repo.match.Points2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertPoints2PUseCase(private val repository: Points2PRepository) :
    UseCase<Points2PUi, Long> {
    override suspend fun execute(params: Points2PUi): Long {
        return repository.insert(params)
    }
}