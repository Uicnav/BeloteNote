package com.ionvaranita.belotenote.datalayer.database.entity.players3

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.utils.getCurrentTime

@Entity
data class Game3PEntity(@PrimaryKey(
    autoGenerate = true) val idGame: Short = 0, val dateGame: Long = getCurrentTime(), val statusGame: Byte = GameStatus.CONTINUE.id, val winnerPoints: Short, val name1: String, val name2: String, val name3: String) {
    fun toUiModel(): Game3PUi {
        return Game3PUi(idGame = this.idGame, statusGame = this.statusGame,winnerPoints = this.winnerPoints, name1 = this.name1, name2 = this.name2, name3 = this.name3)
    }
}
