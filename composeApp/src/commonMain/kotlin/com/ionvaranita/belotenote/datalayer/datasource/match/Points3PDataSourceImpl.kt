package com.ionvaranita.belotenote.datalayer.datasource.match

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity
import com.ionvaranita.belotenote.domain.datasource.match.Points3PDataSource
import kotlinx.coroutines.flow.Flow
import varanita.informatics.shared.database.dao.players3.Points3PDao

class Points3PDataSourceImpl(private val dao: Points3PDao) : Points3PDataSource {
    override suspend fun insert(entity: Points3PEntity): Long {
        return dao.insert(entity)
    }

    override fun getPoints(idGame: Int): Flow<List<Points3PEntity>> {
        return dao.getPoints(idGame)
    }

    override suspend fun getLastPoints(idGame: Int): Points3PEntity? {
        return dao.getLastPoints(idGame)
    }

    override suspend fun delete(idGame: Int): Int {
        return dao.delete(idGame)
    }

    override suspend fun delete(row: Points3PEntity) {
        dao.delete(row)
    }
}