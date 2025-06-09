package com.ionvaranita.belotenote.domain.datasource.match

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import kotlinx.coroutines.flow.Flow

interface Points2PDataSource {
    suspend fun insert(entity: Points2PEntity): Long

    fun getPoints(idGame: Int): Flow<List<Points2PEntity>>

    suspend fun getLastPoints(idGame: Int): Points2PEntity?

    suspend fun delete(idGame: Int): Int

    suspend fun delete(row: Points2PEntity)

    suspend fun deleteAllPoints(idGame: Int)
}