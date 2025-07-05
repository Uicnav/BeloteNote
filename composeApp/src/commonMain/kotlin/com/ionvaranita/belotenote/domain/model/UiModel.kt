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
    val winningPoints: Short,
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
            winningPoints = this.winningPoints,
            name1 = this.name1,
            name2 = this.name2,
            name3 = this.name3
        )
    }
}

data class Game4PUi(
    val idGame: Int,
    val statusGame: Byte,
    val winningPoints: Short,
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
            winningPoints = this.winningPoints,
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
    var pointsP1: String,
    var pointsP2: String,
    val pointsGame: String,
    var isBoltP1: Boolean = false,
    var isBoltP2: Boolean = false
) {
    fun add(lastPoints: Points2PUi): Points2PUi {
        return this.copy(
            pointsP1 = (this.pointsP1.toShortCustom() + (if (isBoltP1) 0 else lastPoints.pointsP1.toShortCustom())).toString(),
            pointsP2 = (this.pointsP2.toShortCustom() + (if (isBoltP2) 0 else lastPoints.pointsP2.toShortCustom())).toString(),
        )
    }

    fun toDataClass(): Points2PEntity {
        return Points2PEntity(
            id = this.id,
            idGame = this.idGame,
            pointsP1 = this.pointsP1.toShortCustom(),
            pointsP2 = this.pointsP2.toShortCustom(), pointsGame = this.pointsGame.toShortCustom(),
            isBoltP1 = this.isBoltP1,
            isBoltP2 = this.isBoltP2
        )
    }
}

data class Points3PUi(
    val id: Int = 0,
    var idGame: Int = 0,
    val pointsGame: String,
    var pointsP1: String,
    var pointsP2: String,
    var pointsP3: String,
    var isBoltP1: Boolean = false,
    var isBoltP2: Boolean = false,
    var isBoltP3: Boolean = false
) {
    fun add(lastPoints: Points3PUi): Points3PUi {
        return this.copy(
            pointsP1 = (this.pointsP1.toShortCustom() + (if (isBoltP1) 0 else lastPoints.pointsP1.toShortCustom())).toString(),
            pointsP2 = (this.pointsP2.toShortCustom() + (if (isBoltP2) 0 else lastPoints.pointsP2.toShortCustom())).toString(),
            pointsP3 = (this.pointsP3.toShortCustom() + (if (isBoltP3) 0 else lastPoints.pointsP3.toShortCustom())).toString(),
        )
    }

    fun toDataClass(): Points3PEntity {
        return Points3PEntity(
            id = this.id,
            idGame = this.idGame,
            pointsP1 = this.pointsP1.toShortCustom(),
            pointsP2 = this.pointsP2.toShortCustom(),
            pointsP3 = this.pointsP3.toShortCustom(),
            pointsGame = this.pointsGame.toShortCustom(),
            isBoltP1 = this.isBoltP1,
            isBoltP2 = this.isBoltP2,
            isBoltP3 = isBoltP3
        )
    }
}

data class Points4PUi(
    val id: Int = 0,
    var idGame: Int = 0,
    val pointsGame: String,
    var pointsP1: String,
    var pointsP2: String,
    var pointsP3: String,
    var pointsP4: String,
    var isBoltP1: Boolean = false,
    var isBoltP2: Boolean = false,
    var isBoltP3: Boolean = false,
    var isBoltP4: Boolean = false
) {
    fun add(lastPoints: Points4PUi): Points4PUi {
        return this.copy(
            pointsP1 = (this.pointsP1.toShortCustom() + (if (isBoltP1) 0 else lastPoints.pointsP1.toShortCustom())).toString(),
            pointsP2 = (this.pointsP2.toShortCustom() + (if (isBoltP2) 0 else lastPoints.pointsP2.toShortCustom())).toString(),
            pointsP3 = (this.pointsP3.toShortCustom() + (if (isBoltP3) 0 else lastPoints.pointsP3.toShortCustom())).toString(),
            pointsP4 = (this.pointsP4.toShortCustom() + (if (isBoltP4) 0 else lastPoints.pointsP4.toShortCustom())).toString(),
        )
    }

    fun toDataClass(): Points4PEntity {
        return Points4PEntity(
            id = this.id,
            idGame = this.idGame,
            pointsP1 = this.pointsP1.toShortCustom(),
            pointsP2 = this.pointsP2.toShortCustom(),
            pointsP3 = this.pointsP3.toShortCustom(),
            pointsP4 = this.pointsP4.toShortCustom(), pointsGame = this.pointsGame.toShortCustom(),
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
    var pointsP1: String,
    var pointsP2: String,
    val pointsGame: String,
    var isBoltP1: Boolean = false,
    var isBoltP2: Boolean = false
) {
    fun add(lastPoints: Points2GroupsUi): Points2GroupsUi {
        return this.copy(
            pointsP1 = (this.pointsP1.toShortCustom() + (if (isBoltP1) 0 else lastPoints.pointsP1.toShortCustom())).toString(),
            pointsP2 = (this.pointsP2.toShortCustom() + (if (isBoltP2) 0 else lastPoints.pointsP2.toShortCustom())).toString(),
        )
    }

    fun toDataClass(): Points2GroupsEntity {
        return Points2GroupsEntity(
            id = this.id,
            idGame = this.idGame,
            pointsP1 = this.pointsP1.toShortCustom(),
            pointsP2 = this.pointsP2.toShortCustom(),
            pointsGame = this.pointsGame.toShortCustom(),
            isBoltP1 = this.isBoltP1,
            isBoltP2 = this.isBoltP2
        )
    }
}

fun String.toShortCustom(): Short {
    return if (this.isEmpty() || this.contains(BOLT)) {
        0
    } else this.toShort()
}
