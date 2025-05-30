package com.ionvaranita.belotenote.domain.datasource.match

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import kotlinx.coroutines.flow.Flow

interface Points2GroupsDataSource {
    suspend fun insert(points2GroupsEntity: Points2GroupsEntity): Long

    fun getPoints(idGame: Int): Flow<List<Points2GroupsEntity>>

    suspend fun getLastPoints(idGame: Int): Points2GroupsEntity?

    suspend fun delete(idGame: Int): Int

    suspend fun countBoltsByWe(idGame: Int): Int

    suspend fun countBoltsByYouP(idGame: Int): Int

    suspend fun deleteAllBoltWe(idGame: Int)

    suspend fun deleteAllBoltYouP(idGame: Int)
}