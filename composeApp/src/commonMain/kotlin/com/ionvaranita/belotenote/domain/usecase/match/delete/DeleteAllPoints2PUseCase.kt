package com.ionvaranita.belotenote.domain.usecase.match.delete

import com.ionvaranita.belotenote.domain.repo.match.Points2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteAllPoints2PUseCase(private val repository: Points2PRepository) : UseCase<Int, Unit> {
    override suspend fun execute(params: Int) {
        repository.delete(idGame = params)
    }
}