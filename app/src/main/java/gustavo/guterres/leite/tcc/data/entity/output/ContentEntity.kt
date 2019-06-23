package gustavo.guterres.leite.tcc.data.entity.output

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_table")
data class ContentEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var description: String,

    var spotlights: List<SpotlightEntity>
)