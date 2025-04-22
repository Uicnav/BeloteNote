package varanita.informatics.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ionvaranita.belotenote.datalayer.database.entity.BoltManagerEntity

/**
 * Created by ionvaranita on 2019-09-02;
 */
@Dao
interface BoltManagerDao {
    @Insert
    suspend fun insert(boltManagerEntity: BoltManagerEntity)

    @Update
    suspend fun update(boltManagerEntity: BoltManagerEntity)

    @Query("delete from BoltManagerEntity where path = :path and idGame = :idGame")
    suspend fun delete(path: String, idGame: Short)

    @Query("select * from BoltManagerEntity where path = :path and idGame = :idGame and idPerson = :idPerson")
    suspend fun getIsMeno10(path: String, idGame: Short, idPerson: Int): BoltManagerEntity
}
