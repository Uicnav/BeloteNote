package com.ionvaranita.belotenote.datalayer.database.entity.players4

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ionvaranita on 2019-09-11;
 */
@Entity
data class Points4PEntity(@PrimaryKey(
    autoGenerate = true) var id: Int = 0, val idGame: Short, val pointsGame: Short, val pointsP1: Short = 0, val pointsP2: Short = 0, val pointsP3: Short = 0, val pointsP4: Short = 0)
