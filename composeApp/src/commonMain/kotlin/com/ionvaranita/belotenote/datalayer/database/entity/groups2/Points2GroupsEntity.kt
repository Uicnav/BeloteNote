package com.ionvaranita.belotenote.datalayer.database.entity.groups2

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.domain.model.Points2GroupsUi
import com.ionvaranita.belotenote.domain.model.Points2PUi

/**
 * Created by ionvaranita on 20/11/17.
 */
@Entity(foreignKeys = [ForeignKey(entity = Game2GroupsEntity::class, parentColumns = ["idGame"], childColumns = ["idGame"], onDelete = ForeignKey.CASCADE)])
data class Points2GroupsEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0, val idGame: Int, val pointsWe: Short = 0, val pointsYouP: Short = 0, val pointsGame: Short= 0, val boltWe:Boolean = false, val boltYouP: Boolean = false) {
    fun toUiModel(): Points2GroupsUi {
        return Points2GroupsUi(idGame = this.idGame, pointsWe = this.pointsWe.toString(), pointsYouP = this.pointsYouP.toString(), pointsGame = this.pointsGame.toString(), boltWe = this.boltWe, boltYouP = this.boltYouP)
    }
}
