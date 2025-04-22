package com.ionvaranita.belotenote.datalayer.database.entity

import androidx.room.Entity
import androidx.room.Index

/**
 * Created by ionvaranita on 2019-09-02;
 */
@Entity(indices = [Index(value = ["path", "idGame", "idRow"], unique = true)], primaryKeys = ["path", "idGame", "idRow"])
data class BoltEntity(val path: String, val idRow: Int, val idGame: Short, val idPersonBolt: Int, var nrBolt: Byte)
