package gustavo.guterres.leite.tcc.data.entity.output

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spotlight_table")
data class SpotlightEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var value: Double?,

    var image: String
)