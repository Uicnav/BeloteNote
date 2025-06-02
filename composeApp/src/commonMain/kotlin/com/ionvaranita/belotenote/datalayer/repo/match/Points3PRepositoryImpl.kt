package com.ionvaranita.belotenote.datalayer.repo.match

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity
import com.ionvaranita.belotenote.domain.datasource.match.Points3PDataSource
import com.ionvaranita.belotenote.domain.repo.match.Points3PRepository
import kotlinx.coroutines.flow.Flow

class Points3PRepositoryImpl(private val datasource: Points3PDataSource) : Points3PRepository {
    override suspend fun insert(entity: Points3PEntity): Long {
        return datasource.insert(entity)
    }

    override fun getPoints(idGame: Int): Flow<List<Points3PEntity>> {
        return datasource.getPoints(idGame)
    }

    override suspend fun getLastPoints(idGame: Int): Points3PEntity? {
        return datasource.getLastPoints(idGame)
    }

    override suspend fun delete(idGame: Int): Int {
        return datasource.delete(idGame)
    }

    override suspend fun delete(row: Points3PEntity) {
        datasource.delete(row)
    }

}