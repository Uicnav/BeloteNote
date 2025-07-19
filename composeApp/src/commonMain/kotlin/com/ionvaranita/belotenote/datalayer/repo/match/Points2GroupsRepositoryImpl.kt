package com.ionvaranita.belotenote.datalayer.repo.match

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.domain.datasource.match.Points2GroupsDataSource
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.repo.match.Points2GroupsRepository
import kotlinx.coroutines.flow.Flow

class Points2GroupsRepositoryImpl(private val datasource: Points2GroupsDataSource) :
    Points2GroupsRepository {
    override suspend fun insert(points: Points2GroupsUi): Long {
        return datasource.insert(points.toDataClass())
    }

    override fun getPoints(idGame: Int): Flow<List<Points2GroupsEntity>> {
       return datasource.getPoints(idGame)
    }

    override suspend fun delete(idGame: Int): Int {
        return datasource.delete(idGame)
    }

    override suspend fun delete(row: Points2GroupsEntity) {
        datasource.delete(row)
    }

}