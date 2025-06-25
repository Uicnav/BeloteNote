package com.ionvaranita.belotenote.domain.usecase.match.delete

import com.ionvaranita.belotenote.domain.repo.match.Points3PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteAllPoints3PUseCase(private val repository: Points3PRepository) : UseCase<Int, Unit> {
    override suspend fun execute(params: Int) {
        repository.delete(idGame = params)
    }
}