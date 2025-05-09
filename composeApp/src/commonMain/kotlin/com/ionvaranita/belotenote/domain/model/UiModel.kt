package com.ionvaranita.belotenote.domain.model

import com.ionvaranita.belotenote.datalayer.database.entity.WinningPointsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import kotlinx.serialization.Serializable

@Serializable
data class Game2PUi(val idGame: Int, val statusGame: Byte, val winningPoints: Short, val name1: String, val name2: String, val scoreName1: Short,
    val scoreName2: Short) {
    fun toDataClass(): Game2PEntity {
        return Game2PEntity(idGame = this.idGame, statusGame = this.statusGame, winnerPoints = this.winningPoints, name1 = this.name1, name2 = this.name2, scoreName1 = this.scoreName1, scoreName2 = this.scoreName2)
    }
}

data class Game3PUi(val idGame: Int, val statusGame: Byte, val winnerPoints: Short, val name1: String, val name2: String, val name3: String, val scoreName1: Short, val scoreName2: Short, val scoreName3: Short) {
    fun toDataClass(): Game3PEntity {
        return Game3PEntity(idGame = this.idGame, statusGame = this.statusGame, winnerPoints = this.winnerPoints, name1 = this.name1, name2 = this.name2, name3 = this.name3)
    }
}

data class Game4PUi(val idGame: Int, val statusGame: Byte, val winnerPoints: Short, val name1: String, val name2: String, val name3: String, val name4: String, val scoreName1: Short = 0, val scoreName2: Short, val scoreName3: Short, val scoreName4: Short) {
    fun toDataClass(): Game4PEntity {
        return Game4PEntity(idGame = this.idGame, statusGame = this.statusGame, winnerPoints = this.winnerPoints, name1 = this.name1, name2 = this.name2, name3 = this.name3, name4 = this.name4)
    }
}

data class Game2GroupsUi(val idGame: Int, val statusGame: Byte, val winnerPoints: Short, val name1: String, val name2: String,val scoreName1: Short, var scoreName2: Short) {
    fun toDataClass(): Game2GroupsEntity {
        return Game2GroupsEntity(idGame = this.idGame, statusGame = this.statusGame, winnerPoints = this.winnerPoints, name1 = this.name1, name2 = this.name2)
    }
}

data class WinningPointsUi(val winningPoints: Short) {
    fun toDataClass(): WinningPointsEntity {
        return WinningPointsEntity(winningPoints = this.winningPoints)
    }
}

data class Score2PUi(val scorMe: Short, val scorYouS: Short)