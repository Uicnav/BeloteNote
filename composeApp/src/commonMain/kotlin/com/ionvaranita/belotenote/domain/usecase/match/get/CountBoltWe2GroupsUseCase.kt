package com.ionvaranita.belotenote.domain.usecase.match.get

import com.ionvaranita.belotenote.domain.repo.match.Points2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class CountBoltWe2GroupsUseCase(private val repository: Points2GroupsRepository) :
    UseCase<Int, Int> {
    override suspend fun execute(params: Int): Int {
        return repository.countBoltsByWe(idGame = params)
    }
}