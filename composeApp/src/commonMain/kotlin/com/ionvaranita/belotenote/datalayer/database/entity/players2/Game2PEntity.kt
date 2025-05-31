package com.ionvaranita.belotenote.datalayer.database.entity.players2

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.utils.getCurrentTime
import com.ionvaranita.belotenote.constants.GameStatus

@Entity
data class Game2PEntity(
    @PrimaryKey(autoGenerate = true) val idGame: Int = 0,
    val dateGame: Long = getCurrentTime(), // Default value
    val statusGame: Byte = GameStatus.CONTINUE.id,
    val winnerPoints: Short,
    val name1: String,
    val name2: String,
    val scoreName1: Short = 0,
    val scoreName2: Short = 0,
                       ) {
    fun toUiModel(): Game2PUi {
        return Game2PUi(idGame = this.idGame, statusGame = this.statusGame, winningPoints = this.winnerPoints, name1 = this.name1, name2 = this.name2,scoreName1 = this.scoreName1, scoreName2 = this.scoreName2)
    }
}
