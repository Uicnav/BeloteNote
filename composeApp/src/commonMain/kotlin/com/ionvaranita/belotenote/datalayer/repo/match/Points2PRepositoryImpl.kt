package com.ionvaranita.belotenote.datalayer.repo.match

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import com.ionvaranita.belotenote.domain.datasource.match.Points2PDataSource
import com.ionvaranita.belotenote.domain.repo.match.Points2PRepository
import kotlinx.coroutines.flow.Flow

class Points2PRepositoryImpl(private val datasource: Points2PDataSource) : Points2PRepository {
    override suspend fun insert(entity: Points2PEntity): Long {
        return datasource.insert(entity)
    }

    override fun getPoints(idGame: Int): Flow<List<Points2PEntity>> {
        return datasource.getPoints(idGame)
    }

    override suspend fun getLastPoints(idGame: Int): Points2PEntity? {
        return datasource.getLastPoints(idGame)
    }

    override suspend fun delete(idGame: Int): Int {
        return datasource.delete(idGame)
    }

    override suspend fun delete(row: Points2PEntity) {
        datasource.delete(row)
    }

    override suspend fun deleteAllPoints(idGame: Int) {
        datasource.deleteAllPoints(idGame)
    }

}