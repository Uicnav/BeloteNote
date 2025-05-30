package com.ionvaranita.belotenote.domain.usecase.bolt.insert

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Bolt2GroupsEntity
import com.ionvaranita.belotenote.domain.repo.bolt.Bolt2GroupsRepository
import com.ionvaranita.belotenote.domain.usecase.UseCase

class InsertBolt2GroupsUseCase(private val repository: Bolt2GroupsRepository) : UseCase<Bolt2GroupsEntity, Unit> {
    override suspend fun execute(params: Bolt2GroupsEntity) =  repository.insertBolt(bolt2GroupsEntity = params)
}