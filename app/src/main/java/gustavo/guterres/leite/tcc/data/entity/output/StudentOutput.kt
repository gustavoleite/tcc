package gustavo.guterres.leite.tcc.data.entity.output

import com.google.firebase.database.Exclude

data class StudentOutput(
    @Exclude var id: String = "",
    val name: String = "",
    val hits: Int = 0,
    val mistakes: Int = 0,
    val currentLevel: String = "",
    val accumulatedPoints: Int = 0
)