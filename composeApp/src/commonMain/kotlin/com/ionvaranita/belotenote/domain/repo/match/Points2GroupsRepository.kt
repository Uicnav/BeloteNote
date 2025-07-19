package com.ionvaranita.belotenote.domain.repo.match

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import kotlinx.coroutines.flow.Flow

interface Points2GroupsRepository {
    suspend fun insert(points: Points2GroupsUi): Long

    fun getPoints(idGame: Int): Flow<List<Points2GroupsEntity>>


    suspend fun delete(idGame: Int): Int


    suspend fun delete(row: Points2GroupsEntity)
}