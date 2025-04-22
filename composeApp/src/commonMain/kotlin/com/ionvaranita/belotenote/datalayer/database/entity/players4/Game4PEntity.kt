package com.ionvaranita.belotenote.datalayer.database.entity.players4

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.utils.getCurrentTime
import varanita.informatics.shared.constants.GameStatus

/**
 * Created by ionvaranita on 2019-09-11;
 */
@Entity
data class Game4PEntity(@PrimaryKey(
    autoGenerate = true) val idGame: Short = 0, val dateGame: Long = getCurrentTime(), val statusGame: Byte = GameStatus.CONTINUE, val winnerPoints: Short, val name1: String, val name2: String, val name3: String, val name4: String)
