package com.ionvaranita.belotenote.datalayer.database.entity.groups2

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Created by ionvaranita on 09/12/2017.
 */
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Game2GroupsEntity::class,
            parentColumns = ["idGame"],
            childColumns = ["idGame"],
            onDelete = ForeignKey.CASCADE
                  )
    ]
       )
data class Scor2GroupsEntity (
    @PrimaryKey
    var idGame: Int = 0,
    var scorWe: Short = 0,
    var scorYouP: Short = 0
)
