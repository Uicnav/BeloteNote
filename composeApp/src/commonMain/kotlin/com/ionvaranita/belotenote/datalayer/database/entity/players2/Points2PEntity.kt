package com.ionvaranita.belotenote.datalayer.database.entity.players2

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.domain.model.Game2PUi
import com.ionvaranita.belotenote.domain.model.Points2PUi

@Entity(foreignKeys = [ForeignKey(entity = Game2PEntity::class, parentColumns = ["idGame"], childColumns = ["idGame"], onDelete = ForeignKey.CASCADE)])
data class Points2PEntity(@PrimaryKey(autoGenerate = true) var id: Int = 0, val idGame: Int, val pointsMe: Short = 0, val pointsYouS: Short = 0, val pointsGame: Short){
    fun toUiModel(): Points2PUi {
        return Points2PUi(idGame = this.idGame, pointsMe = this.pointsMe, pointsYouS = this.pointsYouS, pointsGame = this.pointsGame)
    }
}
