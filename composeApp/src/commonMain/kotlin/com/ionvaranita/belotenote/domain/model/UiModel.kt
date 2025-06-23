package com.ionvaranita.belotenote.domain.model

import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Game2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Game2PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players2.Points2PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Game3PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players3.Points3PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Game4PEntity
import com.ionvaranita.belotenote.datalayer.database.entity.players4.Points4PEntity
import com.ionvaranita.belotenote.ui.match.BOLT
import kotlinx.serialization.Serializable

@Serializable
data class Game2PUi(
    val idGame: Int,
    val statusGame: Byte,
    val winningPoints: Short,
    val name1: String,
    val name2: String,
    val scoreName1: Short,
    val scoreName2: Short
) {
    fun toDataClass(): Game2PEntity {
        return Game2PEntity(
            idGame = this.idGame,
            statusGame = this.statusGame,
            winningPoints = this.winningPoints,
            name1 = this.name1,
            name2 = this.name2,
            scoreName1 = this.scoreName1,
            scoreName2 = this.scoreName2
        )
    }
}

data class Game3PUi(
    val idGame: Int,
    val statusGame: Byte,
    val winnerPoints: Short,
    val name1: String,
    val name2: String,
    val name3: String,
    val scoreName1: Short,
    val scoreName2: Short,
    val scoreName3: Short
) {
    fun toDataClass(): Game3PEntity {
        return Game3PEntity(
            idGame = this.idGame,
            statusGame = this.statusGame,
            winnerPoints = this.winnerPoints,
            name1 = this.name1,
            name2 = this.name2,
            name3 = this.name3
        )
    }
}

data class Game4PUi(
    val idGame: Int,
    val statusGame: Byte,
    val winnerPoints: Short,
    val name1: String,
    val name2: String,
    val name3: String,
    val name4: String,
    val scoreName1: Short = 0,
    val scoreName2: Short,
    val scoreName3: Short,
    val scoreName4: Short
) {
    fun toDataClass(): Game4PEntity {
        return Game4PEntity(
            idGame = this.idGame,
            statusGame = this.statusGame,
            winnerPoints = this.winnerPoints,
            name1 = this.name1,
            name2 = this.name2,
            name3 = this.name3,
            name4 = this.name4
        )
    }
}

data class Game2GroupsUi(
    val idGame: Int,
    val statusGame: Byte,
    val winningPoints: Short,
    val name1: String,
    val name2: String,
    val scoreName1: Short,
    var scoreName2: Short
) {
    fun toDataClass(): Game2GroupsEntity {
        return Game2GroupsEntity(
            idGame = this.idGame,
            statusGame = this.statusGame,
            winningPoints = this.winningPoints,
            name1 = this.name1,
            name2 = this.name2
        )
    }
}

data class Points2PUi(
    val id: Int = 0,
    var idGame: Int = 0,
    var pointsMe: String,
    var pointsYouS: String,
    val pointsGame: String,
    var isBoltMe: Boolean = false,
    var isBoltYouS: Boolean = false
) {
    fun add(lastPoints: Points2PEntity): Points2PEntity {
        return Points2PEntity(
            id = this.id,
            idGame = this.idGame,
            pointsMe = (this.pointsMe.toShortCustom() + lastPoints.pointsMe).toShort(),
            pointsYouS = (this.pointsYouS.toShortCustom() + lastPoints.pointsYouS).toShort(),
            pointsGame = this.pointsGame.toShort(),
            isBoltMe = this.isBoltMe,
            isBoltYouS = this.isBoltYouS,
        )
    }
    fun toDataClass(): Points2PEntity {
        return Points2PEntity(id = this.id, idGame = this.idGame, pointsMe = this.pointsMe.toShortCustom(), pointsYouS = this.pointsYouS.toShortCustom(), pointsGame = this.pointsGame.toShort(), isBoltMe = this.isBoltMe, isBoltYouS = this.isBoltYouS)
    }
}

data class Points3PUi(
    var idGame: Int = 0,
    val pointsGame: String,
    var pointsP1: String,
    var pointsP2: String,
    var pointsP3: String,
    var isBoltP1: Boolean = false,
    var isBoltP2: Boolean = false,
    var isBoltP3: Boolean = false
) {
    fun add(lastPoints: Points3PEntity): Points3PEntity {
        return Points3PEntity(
            idGame = this.idGame,
            pointsP1 = (this.pointsP1.toShortCustom() + lastPoints.pointsP1).toShort(),
            pointsP2 = (this.pointsP2.toShortCustom() + lastPoints.pointsP2).toShort(),
            pointsP3 = (this.pointsP3.toShortCustom() + lastPoints.pointsP3).toShort(),
            pointsGame = this.pointsGame.toShort(),
            isBoltP1 = this.isBoltP1,
            isBoltP2 = this.isBoltP2,
            isBoltP3 = this.isBoltP3
        )
    }
}

data class Points4PUi(
    val idGame: Int,
    val pointsGame: String,
    val pointsP1: String,
    val pointsP2: String,
    val pointsP3: String,
    val pointsP4: String,
    val isBoltP1: Boolean = false,
    val isBoltP2: Boolean = false,
    val isBoltP3: Boolean = false,
    val isBoltP4: Boolean = false
) {
    fun add(lastPoints: Points4PEntity): Points4PEntity {
        return Points4PEntity(
            idGame = this.idGame,
            pointsP1 = (this.pointsP1.toShortCustom() + lastPoints.pointsP1).toShort(),
            pointsP2 = (this.pointsP2.toShortCustom() + lastPoints.pointsP2).toShort(),
            pointsP3 = (this.pointsP3.toShortCustom() + lastPoints.pointsP3).toShort(),
            pointsP4 = (this.pointsP4.toShortCustom() + lastPoints.pointsP4).toShort(),
            pointsGame = this.pointsGame.toShort(),
            isBoltP1 = this.isBoltP1,
            isBoltP2 = this.isBoltP2,
            isBoltP3 = this.isBoltP3,
            isBoltP4 = this.isBoltP4
        )
    }
}


data class Points2GroupsUi(
    val id: Int = 0,
    var idGame: Int = 0,
    var pointsWe: String,
    var pointsYouP: String,
    val pointsGame: String,
    var isBoltWe: Boolean = false,
    var isBoltYouP: Boolean = false
) {
    fun add(lastPoints: Points2GroupsEntity): Points2GroupsEntity {
        return Points2GroupsEntity(
            id = this.id,
            idGame = this.idGame,
            pointsWe = (this.pointsWe.toShortCustom() + lastPoints.pointsWe).toShort(),
            pointsYouP = (this.pointsYouP.toShortCustom() + lastPoints.pointsYouP).toShort(),
            pointsGame = this.pointsGame.toShort(),
            isBoltWe = this.isBoltWe,
            isBoltYouP = this.isBoltYouP
        )
    }

    fun toDataClass(): Points2GroupsEntity {
        return Points2GroupsEntity(id = this.id, idGame = this.idGame, pointsWe = this.pointsWe.toShortCustom(), pointsYouP = this.pointsYouP.toShortCustom(), pointsGame = this.pointsGame.toShort(), isBoltWe = this.isBoltWe, isBoltYouP = this.isBoltYouP)
    }
}

fun String.toShortCustom(): Short {
    return if (this.isEmpty() || this == BOLT) {
        0
    } else this.toShort()
}
