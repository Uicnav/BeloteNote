package com.ionvaranita.belotenote.domain.model

import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import varanita.informatics.shared.constants.GameStatus

data class Game2PUi(val idGame: Short = 0, val statusGame: Byte = GameStatus.CONTINUE, val winnerPoints: Short, val name1: String, val name2: String) {
    fun toDataClass(): Game2PEntity{
        return Game2PEntity(idGame = this.idGame, statusGame = this.statusGame, winnerPoints = this.winnerPoints, name1 = this.name1, name2 = this.name2
                           )
    }
}