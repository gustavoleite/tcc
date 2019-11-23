package gustavo.guterres.leite.tcc.data.entity.output

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level_table")
data class LevelEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var name: String,

    var steps: List<StepEntity>
)
