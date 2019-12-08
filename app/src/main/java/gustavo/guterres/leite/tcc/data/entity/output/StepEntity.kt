package gustavo.guterres.leite.tcc.data.entity.output

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "step_table")
data class StepEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var points: Double,

    var content: ContentEntity,

    var actions: List<ActionEntity>,

    var rightActionId: Int
)
