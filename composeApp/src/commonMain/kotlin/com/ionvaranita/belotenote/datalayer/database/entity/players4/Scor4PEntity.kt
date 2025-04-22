package com.ionvaranita.belotenote.datalayer.database.entity.players4

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ionvaranita on 2019-09-11;
 */
@Entity
data class Scor4PEntity(@PrimaryKey var idGame: Short = 0, var scorP1: Short = 0, var scorP2: Short = 0, var scorP3: Short = 0, var scorP4: Short = 0)
