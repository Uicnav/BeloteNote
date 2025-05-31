package com.ionvaranita.belotenote.datalayer.datasource.bolt

import com.ionvaranita.belotenote.datalayer.database.dao.groups2.Bolt2GroupsDao
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Bolt2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.domain.datasource.bolt.Bolt2GroupsDataSource
import com.ionvaranita.belotenote.domain.datasource.game.Game2GroupsDataSource
import kotlinx.coroutines.flow.Flow
import varanita.informatics.shared.database.dao.groups2.Game2GroupsDao

class Bolt2GroupsDataSourceImpl(private val dao: Bolt2GroupsDao) : Bolt2GroupsDataSource {
    override suspend fun insertBolt(bolt2GroupsEntity: Bolt2GroupsEntity) {
        dao.insert(bolt2GroupsEntity)
    }

    override fun getBolts(idGame: Int): Flow<List<Bolt2GroupsEntity>> {
        return dao.getBolts(idGame)
    }

    override suspend fun countBoltsByPerson(idGame: Int, idPerson: Int): Int {
        return dao.countBoltsByPerson(idGame, idPerson)
    }
}