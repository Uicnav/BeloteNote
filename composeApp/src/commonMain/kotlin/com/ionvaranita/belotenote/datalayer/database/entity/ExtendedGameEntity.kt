package com.ionvaranita.belotenote.datalayer.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ionvaranita on 2019-08-30;
 */
@Entity
data class ExtendedGameEntity(val path: String, @PrimaryKey val idGame: Short = 0, val idWinner: Int, val maxPoints: Short)
