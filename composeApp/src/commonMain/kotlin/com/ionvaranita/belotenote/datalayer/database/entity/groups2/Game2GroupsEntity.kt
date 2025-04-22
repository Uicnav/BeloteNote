package com.ionvaranita.belotenote.datalayer.database.entity.groups2

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.utils.getCurrentTime
import varanita.informatics.shared.constants.GameStatus

/**
 * Created by ionvaranita on 09/12/2017.
 */
@Entity
data class Game2GroupsEntity(@PrimaryKey(
    autoGenerate = true) val idGame: Short = 0, val dateGame: Long = getCurrentTime(), val statusGame: Byte = GameStatus.CONTINUE, val winnerPoints: Short, val name1: String, val name2: String)
