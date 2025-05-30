package com.ionvaranita.belotenote.domain.usecase.match.get.delete

import com.ionvaranita.belotenote.domain.repo.match.Points2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class DeleteBoltYouP2GroupsUseCase(private val repository: Points2GroupsRepository) :
    UseCase<Int, Int> {
    override suspend fun execute(params: Int): Int {
        return repository.countBoltsByYouP(idGame = params)
    }
}