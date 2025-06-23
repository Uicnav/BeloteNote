package com.ionvaranita.belotenote.datalayer.database.entity.players2

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.domain.model.Points2PUi

@Entity(foreignKeys = [ForeignKey(entity = Game2PEntity::class, parentColumns = ["idGame"], childColumns = ["idGame"], onDelete = ForeignKey.CASCADE)])
data class Points2PEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0, val idGame: Int, val pointsMe: Short = 0, val pointsYouS: Short = 0, val pointsGame: Short = 0, val isBoltMe: Boolean = false, val isBoltYouS: Boolean = false){
    fun toUiModel(): Points2PUi {
        return Points2PUi(id = this.id, idGame = this.idGame, pointsMe = this.pointsMe.toString(), pointsYouS = this.pointsYouS.toString(), pointsGame = this.pointsGame.toString(), isBoltMe = this.isBoltMe, isBoltYouS = this.isBoltYouS)
    }
}
