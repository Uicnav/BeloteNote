package varanita.informatics.shared.database.dao.groups2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionvaranita.belotenote.datalayer.database.entity.groups2.Points2GroupsEntity

/**
 * Created by ionvaranita on 20/11/17.
 */
@Dao
interface Points2GroupsDao {
    @Insert
    suspend fun insert(points2GroupsEntity: Points2GroupsEntity): Long

    @Query("select * from Points2GroupsEntity where idGame = :idGame")
    suspend fun getPoints(idGame: Short): List<Points2GroupsEntity>

    @Query("select * from Points2GroupsEntity where id = (select max(id) from Points2GroupsEntity where idGame = :idGame)")
    suspend fun getLastPoints(idGame: Short): Points2GroupsEntity

    @Query("delete from Points2GroupsEntity where idGame = :idGame")
    suspend fun delete(idGame: Short): Int
}