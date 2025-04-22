package varanita.informatics.shared.database.entity.players2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Points2PEntity(@PrimaryKey(autoGenerate = true) var id: Int = 0, val idGame: Short, val pointsMe: Short = 0, val pointsYouS: Short = 0, val pointsGame: Short)
