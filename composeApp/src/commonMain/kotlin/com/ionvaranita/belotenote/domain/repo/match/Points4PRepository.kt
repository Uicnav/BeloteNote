package com.ionvaranita.belotenote.domain.repo.match

import com.ionvaranita.belotenote.datalayer.database.entity.players4.Points4PEntity
import com.ionvaranita.belotenote.domain.model.Points4PUi
import kotlinx.coroutines.flow.Flow

interface Points4PRepository {
    suspend fun insert(entity: Points4PUi): Long

    fun getPoints(idGame: Int): Flow<List<Points4PEntity>>

    suspend fun delete(idGame: Int): Int

    suspend fun delete(row: Points4PEntity)
}