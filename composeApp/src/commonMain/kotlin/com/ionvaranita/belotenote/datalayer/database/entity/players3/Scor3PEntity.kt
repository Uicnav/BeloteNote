package com.ionvaranita.belotenote.datalayer.database.entity.players3

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ionvaranita on 2019-09-06;
 */
@Entity
data class Scor3PEntity (
    @PrimaryKey
    var idGame: Short = 0,
    var scorP1: Short = 0,
    var scorP2: Short = 0,
    var scorP3: Short = 0
)
