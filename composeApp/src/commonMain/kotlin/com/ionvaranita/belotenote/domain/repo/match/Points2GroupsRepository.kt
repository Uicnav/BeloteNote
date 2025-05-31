package com.ionvaranita.belotenote.domain.repo.match

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import kotlinx.coroutines.flow.Flow

interface Points2GroupsRepository {
    suspend fun insert(points2GroupsEntity: Points2GroupsEntity): Long

    fun getPoints(idGame: Int): Flow<List<Points2GroupsEntity>>

    suspend fun getLastPoints(idGame: Int): Points2GroupsEntity?

    suspend fun delete(idGame: Int): Int


    suspend fun delete(row: Points2GroupsEntity)
}