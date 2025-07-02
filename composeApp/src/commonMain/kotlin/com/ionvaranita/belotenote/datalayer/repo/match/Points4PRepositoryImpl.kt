package com.ionvaranita.belotenote.datalayer.repo.match

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Points4PEntity
import com.ionvaranita.belotenote.domain.datasource.match.Points4PDataSource
import com.ionvaranita.belotenote.domain.repo.match.Points4PRepository
import kotlinx.coroutines.flow.Flow

class Points4PRepositoryImpl(private val datasource: Points4PDataSource) : Points4PRepository {
    override suspend fun insert(entity: Points4PEntity): Long {
        return datasource.insert(entity)
    }

    override fun getPoints(idGame: Int): Flow<List<Points4PEntity>> {
        return datasource.getPoints(idGame)
    }

    override suspend fun delete(idGame: Int): Int {
        return datasource.delete(idGame)
    }

    override suspend fun delete(row: Points4PEntity) {
        datasource.delete(row)
    }

}