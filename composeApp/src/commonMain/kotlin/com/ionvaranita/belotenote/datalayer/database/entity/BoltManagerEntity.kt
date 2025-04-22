package com.ionvaranita.belotenote.datalayer.database.entity

import androidx.room.Entity
import androidx.room.Index

/**
 * Created by ionvaranita on 2019-09-02;
 */
@Entity(indices = [Index(value = ["idPerson", "idGame"], unique = true)], primaryKeys = ["idPerson", "idGame"])
data class BoltManagerEntity(val path: String, val idPerson: Int, val idGame: Short, var minus10: Boolean)
