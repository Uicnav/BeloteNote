package com.ionvaranita.belotenote.datalayer.database.entity.players4

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.constants.GameStatus
import com.ionvaranita.belotenote.domain.model.Game4PUi
import com.ionvaranita.belotenote.utils.getCurrentTime

/**
 * Created by ionvaranita on 2019-09-11;
 */
@Entity
data class Game4PEntity(@PrimaryKey(
    autoGenerate = true) val idGame: Int = 0, val dateGame: Long = getCurrentTime(), val statusGame: Byte = GameStatus.CONTINUE.id, val winnerPoints: Short, val name1: String, val name2: String, val name3: String, val name4: String, val scoreName1: Short = 0, val scoreName2: Short = 0, val scoreName3: Short = 0, val scoreName4: Short = 0) {
    fun toUiModel(): Game4PUi {
        return Game4PUi(idGame = this.idGame, statusGame = this.statusGame, winnerPoints = this.winnerPoints, name1 = this.name1, name2 = this.name2, name3 = this.name3, name4 = this.name4,
                        scoreName1 = this.scoreName1, scoreName2 = this.scoreName2, scoreName3 = this.scoreName3, scoreName4 = this.scoreName4)
    }
}
