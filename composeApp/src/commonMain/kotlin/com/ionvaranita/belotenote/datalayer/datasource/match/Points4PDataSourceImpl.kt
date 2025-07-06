package com.ionvaranita.belotenote.datalayer.datasource.match

import com.ionvaranita.belotenote.datalayer.database.dao.players4.Points4PDao
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Points4PEntity
import com.ionvaranita.belotenote.domain.datasource.match.Points4PDataSource
import kotlinx.coroutines.flow.Flow

class Points4PDataSourceImpl(private val dao: Points4PDao) : Points4PDataSource {
    override suspend fun insert(entity: Points4PEntity): Long {
        return dao.insert(entity)
    }

    override fun getPoints(idGame: Int): Flow<List<Points4PEntity>> {
        return dao.getPoints(idGame)
    }

    override suspend fun delete(idGame: Int): Int {
        return dao.deleteByIdGame(idGame)
    }

    override suspend fun delete(row: Points4PEntity) {
        dao.deleteRow(row)
    }
}