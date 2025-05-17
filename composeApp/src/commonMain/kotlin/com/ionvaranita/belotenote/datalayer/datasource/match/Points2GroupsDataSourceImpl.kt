package com.ionvaranita.belotenote.datalayer.datasource.match

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.domain.datasource.match.Points2GroupsDataSource
import kotlinx.coroutines.flow.Flow
import com.ionvaranita.belotenote.datalayer.database.dao.groups2.Points2GroupsDao

class Points2GroupsDataSourceImpl(private val dao: Points2GroupsDao) :
    Points2GroupsDataSource {
    override suspend fun insert(points2GroupsEntity: Points2GroupsEntity): Long {
        return dao.insert(points2GroupsEntity)
    }

    override fun getPoints(idGame: Int): Flow<List<Points2GroupsEntity>> {
        return  dao.getPoints(idGame)
    }

    override suspend fun getLastPoints(idGame: Int): Points2GroupsEntity {
        TODO("Not yet implemented")
    }

    override suspend fun delete(idGame: Int): Int {
        return dao.delete(idGame)
    }

}