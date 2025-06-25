package com.ionvaranita.belotenote.domain.repo.match

import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity
import kotlinx.coroutines.flow.Flow

interface Points3PRepository {
    suspend fun insert(entity: Points3PEntity): Long

    fun getPoints(idGame: Int): Flow<List<Points3PEntity>>

    suspend fun delete(idGame: Int): Int


    suspend fun delete(row: Points3PEntity)
}