package com.ionvaranita.belotenote.domain.usecase.match.get

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.repo.match.Points2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLastPoints2GroupsUseCase(private val repository: Points2GroupsRepository) :
    UseCase<Int, Points2GroupsEntity?> {
    override suspend fun execute(params: Int): Points2GroupsEntity? {
        return repository.getLastPoints(idGame = params)
    }
}