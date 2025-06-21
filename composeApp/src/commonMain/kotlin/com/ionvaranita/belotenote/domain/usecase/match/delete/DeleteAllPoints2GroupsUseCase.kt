package com.ionvaranita.belotenote.domain.usecase.match.delete

import com.ionvaranita.belotenote.domain.repo.match.Points2GroupsRepository
import com.ionvaranita.belotenote.domain.repo.match.Points2PRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteAllPoints2GroupsUseCase(private val repository: Points2GroupsRepository) : UseCase<Int, Unit> {
    override suspend fun execute(params: Int) {
        repository.delete(idGame = params)
    }
}