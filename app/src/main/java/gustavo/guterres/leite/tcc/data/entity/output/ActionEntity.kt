package gustavo.guterres.leite.tcc.data.entity.output

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "action_table")
data class ActionEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var text: String? = null,

    var image: String? = null
)