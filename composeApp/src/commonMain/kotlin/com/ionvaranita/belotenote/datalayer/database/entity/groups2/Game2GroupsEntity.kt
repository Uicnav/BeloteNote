package com.ionvaranita.belotenote.datalayer.database.entity.groups2

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.utils.getCurrentTime

/**
 * Created by ionvaranita on 09/12/2017.
 */
@Entity
data class Game2GroupsEntity(@PrimaryKey(
    autoGenerate = true) val idGame: Int = 0, val dateGame: Long = getCurrentTime(), val statusGame: Byte = GameStatus.CONTINUE.id, val winningPoints: Short, val name1: String, val name2: String,
                             val scoreName1: Short = 0, var scoreName2: Short = 0) {
    fun toUiModel(): Game2GroupsUi {
        return Game2GroupsUi(idGame = this.idGame, statusGame = this.statusGame, winningPoints = this.winningPoints, name1 = this.name1, name2 = this.name2, scoreName1 = this.scoreName1, scoreName2 = this.scoreName2)
    }
}
