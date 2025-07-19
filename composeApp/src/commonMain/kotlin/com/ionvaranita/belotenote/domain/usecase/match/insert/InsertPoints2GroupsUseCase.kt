package com.ionvaranita.belotenote.domain.usecase.match.insert

import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.repo.match.Points2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertPoints2GroupsUseCase(private val repository: Points2GroupsRepository) :
    UseCase<Points2GroupsUi, Long> {
    override suspend fun execute(params: Points2GroupsUi): Long  {
        return repository.insert(params)
    }
}