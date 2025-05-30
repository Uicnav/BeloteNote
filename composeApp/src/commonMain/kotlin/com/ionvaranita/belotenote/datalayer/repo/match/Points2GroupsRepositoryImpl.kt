package com.ionvaranita.belotenote.datalayer.repo.match

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.domain.datasource.match.Points2GroupsDataSource
import com.ionvaranita.belotenote.domain.repo.match.Points2GroupsRepository
import kotlinx.coroutines.flow.Flow

class Points2GroupsRepositoryImpl(private val datasource: Points2GroupsDataSource) :
    Points2GroupsRepository {
    override suspend fun insert(points2GroupsEntity: Points2GroupsEntity): Long {
        return datasource.insert(points2GroupsEntity)
    }

    override fun getPoints(idGame: Int): Flow<List<Points2GroupsEntity>> {
       return datasource.getPoints(idGame)
    }

    override suspend fun getLastPoints(idGame: Int): Points2GroupsEntity? {
        return datasource.getLastPoints(idGame)
    }

    override suspend fun delete(idGame: Int): Int {
        return datasource.delete(idGame)
    }

    override suspend fun countBoltsByWe(idGame: Int): Int {
        return datasource.countBoltsByWe(idGame)
    }

    override suspend fun countBoltsByYouP(idGame: Int): Int {
        return datasource.countBoltsByYouP(idGame)
    }

    override suspend fun deleteAllBoltWe(idGame: Int) {
        datasource.deleteAllBoltWe(idGame)
    }

    override suspend fun deleteAllBoltYouP(idGame: Int) {
        datasource.deleteAllBoltYouP(idGame)
    }

}