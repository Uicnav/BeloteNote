package com.ionvaranita.belotenote.datalayer.database.entity.players3

import androidx.room.Entity
import androidx.room.PrimaryKey
import varanita.informatics.shared.constants.GameStatus
import com.ionvaranita.belotenote.utils.getCurrentTime

@Entity
data class Game3PEntity(@PrimaryKey(
    autoGenerate = true) val idGame: Short = 0, val dateGame: Long = getCurrentTime(), val statusGame: Byte = GameStatus.CONTINUE, val winnerPoints: Short, val name1: String, val name2: String, val name3: String)
