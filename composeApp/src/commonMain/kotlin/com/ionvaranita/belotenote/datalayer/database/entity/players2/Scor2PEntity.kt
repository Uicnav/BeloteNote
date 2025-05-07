package com.ionvaranita.belotenote.datalayer.database.entity.players2

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity

/**
 * Created by ionvaranita on 09/12/2017.
 */
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Game2PEntity::class,
            parentColumns = ["idGame"],
            childColumns = ["idGame"],
            onDelete = ForeignKey.CASCADE
                  )
    ]
       )
data class Scor2PEntity(
    @PrimaryKey var idGame: Int = 0,
    var scorMe: Short = 0,
    var scorYouS: Short = 0,
                       )


