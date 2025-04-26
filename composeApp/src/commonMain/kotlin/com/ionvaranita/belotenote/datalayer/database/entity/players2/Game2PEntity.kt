package com.ionvaranita.belotenote.datalayer.database.entity.players2

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.utils.getCurrentTime
import varanita.informatics.shared.constants.GameStatus

@Entity
data class Game2PEntity(
    @PrimaryKey(autoGenerate = true) val idGame: Short = 0,
    val dateGame: Long = getCurrentTime(), // Default value
    val statusGame: Byte = GameStatus.CONTINUE,
    val winnerPoints: Short,
    val name1: String,
    val name2: String
                       ) {
    fun toUiModel(): Game2PUi {
        return Game2PUi(statusGame = this.statusGame,winnerPoints = this.winnerPoints, name1 = this.name1, name2 = this.name2)
    }
}
