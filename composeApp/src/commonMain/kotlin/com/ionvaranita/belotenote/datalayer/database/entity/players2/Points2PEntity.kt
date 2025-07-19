package com.ionvaranita.belotenote.datalayer.database.entity.players2

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.domain.model.Points2PUi

@Entity(foreignKeys = [ForeignKey(entity = Game2PEntity::class, parentColumns = ["idGame"], childColumns = ["idGame"], onDelete = ForeignKey.CASCADE)])
data class Points2PEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0, val idGame: Int, val pointsP1: Short = 0, val pointsP2: Short = 0, val pointsGame: Short = 0, val isBoltP1: Boolean = false, val isBoltP2: Boolean = false){
    fun toUiModel(): Points2PUi {
        return Points2PUi(id = this.id, idGame = this.idGame, pointsP1 = this.pointsP1.toString(), pointsP2 = this.pointsP2.toString(), pointsGame = this.pointsGame.toString(), isBoltP1 = this.isBoltP1, isBoltP2 = this.isBoltP2)
    }
}
