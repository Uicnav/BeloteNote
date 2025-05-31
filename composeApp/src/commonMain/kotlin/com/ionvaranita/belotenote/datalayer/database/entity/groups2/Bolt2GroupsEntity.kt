package com.ionvaranita.belotenote.datalayer.database.entity.groups2

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.ionvaranita.belotenote.domain.model.Bolt2GroupsUi
import com.ionvaranita.belotenote.domain.model.Game2GroupsUi

@Entity(indices = [Index(value = [ "idGame", "idRow"], unique = true)], primaryKeys = ["idGame", "idRow"],foreignKeys = [ForeignKey(entity = Game2GroupsEntity::class, parentColumns = ["idGame"], childColumns = ["idGame"], onDelete = ForeignKey.CASCADE)])
data class Bolt2GroupsEntity(val idRow: Long, val idGame: Int, val idPersonBolt: Int) {
    fun toUiModel(): Bolt2GroupsUi {
        return Bolt2GroupsUi(idRow = this.idRow, idGame = this.idGame, idPersonBolt = this.idPersonBolt)
    }
}

