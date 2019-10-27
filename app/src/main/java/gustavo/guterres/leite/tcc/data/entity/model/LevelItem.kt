package gustavo.guterres.leite.tcc.data.entity.model

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

data class LevelItem(
    val item: Level,
    var itemPosition: Int = -1,
    val lastLevelUnlocked: ObservableInt = ObservableInt(),
    val studentLevel: ObservableField<StudentLevel> = ObservableField()
)