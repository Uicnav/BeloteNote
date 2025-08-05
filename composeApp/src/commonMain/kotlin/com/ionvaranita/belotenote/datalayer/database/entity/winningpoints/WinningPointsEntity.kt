package com.ionvaranita.belotenote.datalayer.database.entity.winningpoints

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.domain.model.WinningPointsUi
import com.ionvaranita.belotenote.utils.getCurrentTime

@Entity
data class WinningPointsEntity(
    @PrimaryKey var winningPoints: Short,
    val date: Long = getCurrentTime()
) {
    fun toUiModel(): WinningPointsUi {
        return WinningPointsUi(winningPoints = this.winningPoints)
    }
}