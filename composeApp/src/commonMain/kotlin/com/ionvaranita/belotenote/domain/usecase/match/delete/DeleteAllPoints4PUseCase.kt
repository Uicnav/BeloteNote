package com.ionvaranita.belotenote.domain.usecase.match.delete

import com.ionvaranita.belotenote.domain.repo.match.Points4PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteAllPoints4PUseCase(private val repository: Points4PRepository) : UseCase<Int, Unit> {
    override suspend fun execute(params: Int) {
        repository.delete(idGame = params)
    }
}