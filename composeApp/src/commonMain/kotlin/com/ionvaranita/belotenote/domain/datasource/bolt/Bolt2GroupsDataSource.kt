package com.ionvaranita.belotenote.domain.datasource.bolt

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Bolt2GroupsEntity
import kotlinx.coroutines.flow.Flow

interface Bolt2GroupsDataSource {
    suspend fun insertBolt(bolt2GroupsEntity: Bolt2GroupsEntity)
    fun getBolts(idGame: Int): Flow<List<Bolt2GroupsEntity>>
    suspend fun countBoltsByPerson(idGame: Int, idPerson: Int): Int
}