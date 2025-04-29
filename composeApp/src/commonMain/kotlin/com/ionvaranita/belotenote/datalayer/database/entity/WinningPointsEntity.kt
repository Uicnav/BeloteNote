package com.ionvaranita.belotenote.datalayer.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi
import com.ionvaranita.belotenote.domain.model.WinningPointsUi

@Entity
data class WinningPointsEntity(@PrimaryKey var winningPoints: Short) {
    fun toUiModel(): WinningPointsUi {
        return WinningPointsUi(winningPoints = this.winningPoints)
    }
}
