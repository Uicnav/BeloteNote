package com.ionvaranita.belotenote.datalayer.database.entity.players3

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.domain.model.Game3PUi
import com.ionvaranita.belotenote.utils.getCurrentTime

@Entity
data class Game3PEntity(@PrimaryKey(
    autoGenerate = true) val idGame: Int = 0, val dateGame: Long = getCurrentTime(), val statusGame: Byte = GameStatus.CONTINUE.id, val winningPoints: Short, val name1: String, val name2: String, val name3: String, val scoreName1: Short = 0, val scoreName2: Short = 0, val scoreName3: Short = 0) {
    fun toUiModel(): Game3PUi {
        return Game3PUi(idGame = this.idGame, statusGame = this.statusGame, winningPoints = this.winningPoints, name1 = this.name1, name2 = this.name2, name3 = this.name3, scoreName1 = this.scoreName1, scoreName2 = this.scoreName2, scoreName3 = this.scoreName3)
    }
}
