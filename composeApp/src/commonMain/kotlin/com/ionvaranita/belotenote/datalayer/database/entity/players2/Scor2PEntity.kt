package varanita.informatics.shared.database.entity.players2

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ionvaranita on 09/12/2017.
 */
@Entity
data class Scor2PEntity(
    @PrimaryKey var idGame: Short = 0,
    var scorMe: Short = 0,
    var scorYouS: Short = 0,
                       )


