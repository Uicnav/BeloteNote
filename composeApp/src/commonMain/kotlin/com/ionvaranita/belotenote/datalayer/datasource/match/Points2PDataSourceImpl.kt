package com.ionvaranita.belotenote.datalayer.datasource.match

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import com.ionvaranita.belotenote.domain.datasource.match.Points2PDataSource
import kotlinx.coroutines.flow.Flow
import varanita.informatics.shared.database.dao.players2.Points2PDao

class Points2PDataSourceImpl(private val dao: Points2PDao) : Points2PDataSource {
    override suspend fun insert(entity: Points2PEntity): Long {
        return dao.insert(entity)
    }

    override fun getPoints(idGame: Int): Flow<List<Points2PEntity>> {
        return dao.getPoints(idGame)
    }

    override suspend fun getLastPoints(idGame: Int): Points2PEntity? {
        return dao.getLastPoints(idGame)
    }

    override suspend fun delete(idGame: Int): Int {
        return dao.deleteAll(idGame)
    }

    override suspend fun delete(row: Points2PEntity) {
        dao.deleteAll(row)
    }

    override suspend fun deleteAllPoints(idGame: Int) {
        dao.deleteAll(idGame)
    }
}