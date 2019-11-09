package gustavo.guterres.leite.tcc.data.entity.output

import androidx.annotation.Keep

@Keep
data class StudentLevelOutput(
    var id: String = "",
    var hits: Int = 0,
    var mistakes: Int = 0
)