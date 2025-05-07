package com.ionvaranita.belotenote.datalayer.database.entity.players4

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity

/**
 * Created by ionvaranita on 2019-09-11;
 */
@Entity(foreignKeys = [ForeignKey(entity = Game4PEntity::class, parentColumns = ["idGame"], childColumns = ["idGame"], onDelete = ForeignKey.CASCADE)])
data class Points4PEntity(@PrimaryKey(
    autoGenerate = true) var id: Int = 0, val idGame: Int, val pointsGame: Short, val pointsP1: Short = 0, val pointsP2: Short = 0, val pointsP3: Short = 0, val pointsP4: Short = 0)
