package com.ionvaranita.belotenote.datalayer.database.entity.groups2

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.utils.getCurrentTime
import com.ionvaranita.belotenote.constants.GameStatus

/**
 * Created by ionvaranita on 09/12/2017.
 */
@Entity
data class Game2GroupsEntity(@PrimaryKey(
    autoGenerate = true) val idGame: Short = 0, val dateGame: Long = getCurrentTime(), val statusGame: Byte = GameStatus.CONTINUE.id, val winnerPoints: Short, val name1: String, val name2: String) {
    fun toUiModel(): Game2GroupsUi {
        return Game2GroupsUi(idGame = this.idGame, statusGame = this.statusGame, winnerPoints = this.winnerPoints, name1 = this.name1, name2 = this.name2)
    }
}
