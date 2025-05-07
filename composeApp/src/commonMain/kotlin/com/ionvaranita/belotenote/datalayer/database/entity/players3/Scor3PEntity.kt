package com.ionvaranita.belotenote.datalayer.database.entity.players3

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity

/**
 * Created by ionvaranita on 2019-09-06;
 */
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Game3PEntity::class,
            parentColumns = ["idGame"],
            childColumns = ["idGame"],
            onDelete = ForeignKey.CASCADE
                  )
    ]
       )
data class Scor3PEntity (
    @PrimaryKey
    var idGame: Int = 0,
    var scorP1: Short = 0,
    var scorP2: Short = 0,
    var scorP3: Short = 0
)
