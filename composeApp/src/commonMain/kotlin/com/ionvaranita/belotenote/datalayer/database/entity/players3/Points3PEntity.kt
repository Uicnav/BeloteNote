package com.ionvaranita.belotenote.datalayer.database.entity.players3

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Points3PEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val idGame: Short,
    val pointsGame: Short,
    val pointsP1: Short = 0,
    val pointsP2: Short = 0,
    val pointsP3: Short = 0,
                          )
