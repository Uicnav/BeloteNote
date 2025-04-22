package varanita.informatics.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.ExtendedGameEntity

/**
 * Created by ionvaranita on 2019-08-30;
 */
@Dao
interface ExtendedGameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun merge(extendedGameEntity: ExtendedGameEntity)

    @Query("select * from ExtendedGameEntity where path = :path and idGame = :idGame")
    suspend fun getExtendedGame(path: String, idGame: Short): ExtendedGameEntity

    @Query("delete from ExtendedGameEntity where path = :path and idGame = :idGame")
    suspend fun delete(path: String, idGame: Short)
}