package com.ionvaranita.belotenote.datalayer.database.entity.players4

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.domain.model.Points4PUi

/**
 * Created by ionvaranita on 2019-09-11;
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = Game4PEntity::class,
        parentColumns = ["idGame"],
        childColumns = ["idGame"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Points4PEntity(
    @PrimaryKey(
        autoGenerate = true
    ) var id: Int = 0,
    val idGame: Int,
    val pointsGame: Short,
    val pointsP1: Short = 0,
    val pointsP2: Short = 0,
    val pointsP3: Short = 0,
    val pointsP4: Short = 0,
    val isBoltP1: Boolean = false,
    val isBoltP2: Boolean = false,
    val isBoltP3: Boolean = false,
    val isBoltP4: Boolean = false,
) {
    fun toUiModel(): Points4PUi {
        return Points4PUi(
            id = this.id,
            idGame = this.idGame,
            pointsGame = this.pointsGame.toString(),
            pointsP1 = this.pointsP1.toString(),
            pointsP2 = this.pointsP2.toString(),
            pointsP3 = this.pointsP3.toString(),
            pointsP4 = this.pointsP4.toString(),
            isBoltP1 = this.isBoltP1,
            isBoltP2 = this.isBoltP2,
            isBoltP3 = this.isBoltP3,
            isBoltP4 = this.isBoltP4
        )
    }
}
