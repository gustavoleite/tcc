package gustavo.guterres.leite.tcc.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gustavo.guterres.leite.tcc.data.entity.output.ActionEntity
import gustavo.guterres.leite.tcc.data.entity.output.ContentEntity
import gustavo.guterres.leite.tcc.data.entity.output.LevelEntity
import gustavo.guterres.leite.tcc.data.entity.output.SpotlightEntity
import gustavo.guterres.leite.tcc.data.entity.output.StepEntity

@Database(
    entities = [
        ActionEntity::class,
        ContentEntity::class,
        LevelEntity::class,
        SpotlightEntity::class,
        StepEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class TCCDatabase : RoomDatabase() {

    abstract fun levelDao(): LevelDao
}
