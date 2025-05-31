package com.ionvaranita.belotenote.datalayer.repo.bolt

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Bolt2GroupsEntity
import com.ionvaranita.belotenote.domain.datasource.bolt.Bolt2GroupsDataSource
import com.ionvaranita.belotenote.domain.repo.bolt.Bolt2GroupsRepository
import kotlinx.coroutines.flow.Flow

class Bolt2GroupsRepositoryImpl(private val datasource: Bolt2GroupsDataSource) : Bolt2GroupsRepository {
    override suspend fun insertBolt(bolt2GroupsEntity: Bolt2GroupsEntity) {
        datasource.insertBolt(bolt2GroupsEntity)
    }

    override fun getBolts(idGame: Int): Flow<List<Bolt2GroupsEntity>> {
        return datasource.getBolts(idGame)
    }

    override suspend fun countBoltsByPerson(idGame: Int, idPerson: Int): Int {
        return datasource.countBoltsByPerson(idGame, idPerson)
    }
}