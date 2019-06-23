package gustavo.guterres.leite.tcc.data.room

import androidx.room.*
import gustavo.guterres.leite.tcc.data.entity.output.LevelEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LevelDao {

    @Insert
    fun insert(level: LevelEntity): Completable

    @Query("DELETE FROM level_table")
    fun deleteAll()

    @Query("SELECT * FROM level_table")
    fun getAll(): Single<List<LevelEntity>>
}