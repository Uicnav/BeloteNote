package varanita.informatics.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.BoltEntity

/**
 * Created by ionvaranita on 2019-09-02;
 */
@Dao
interface BoltDao {
    @Insert
    suspend fun insert(boltEntity: BoltEntity)

    @Query("select * from BoltEntity b1 where path = :path and idGame = :idGame and b1.idRow = (select max(idRow) from BoltEntity b2 where b2.idPersonBolt = :idPerson)")
    suspend fun getBolt(path: String, idGame: Short, idPerson: Int): BoltEntity

    @Query("delete from BoltEntity where path = :path and idGame = :idGame")
    suspend fun delete(path: String, idGame: Short)

    @Query("select * from BoltEntity where path = :path and idGame = :idGame")
    suspend fun getBolts(path: String, idGame: Short): List<BoltEntity>
}
