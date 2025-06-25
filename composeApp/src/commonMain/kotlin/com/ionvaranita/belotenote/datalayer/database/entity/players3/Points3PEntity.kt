package com.ionvaranita.belotenote.datalayer.database.entity.players3

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.domain.model.Points2PUi
import com.ionvaranita.belotenote.domain.model.Points3PUi

@Entity(foreignKeys = [ForeignKey(entity = Game3PEntity::class, parentColumns = ["idGame"], childColumns = ["idGame"], onDelete = ForeignKey.CASCADE)])
data class Points3PEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val idGame: Int,
    val pointsGame: Short = 0,
    val pointsP1: Short = 0,
    val pointsP2: Short = 0,
    val pointsP3: Short = 0,
    val isBoltP1: Boolean = false,
    val isBoltP2: Boolean = false,
    val isBoltP3: Boolean = false,

                         ) {
    fun toUiModel(): Points3PUi {
        return Points3PUi(id = this.id, idGame = this.idGame, pointsGame = this.pointsGame.toString(), pointsP1 = this.pointsP1.toString(), pointsP2 = this.pointsP2.toString(), pointsP3 = this.pointsP3.toString(), isBoltP1 = this.isBoltP1, isBoltP2 = this.isBoltP2, isBoltP3 = this.isBoltP3)
    }
}
