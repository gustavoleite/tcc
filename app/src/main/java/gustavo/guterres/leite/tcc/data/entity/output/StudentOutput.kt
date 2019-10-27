package gustavo.guterres.leite.tcc.data.entity.output

import com.google.firebase.database.Exclude
import gustavo.guterres.leite.tcc.data.entity.model.StudentLevel

data class StudentOutput(
    @Exclude var id: String = "",
    val name: String = "",
    val studentLevel: MutableList<StudentLevelOutput>? = null,
    val currentLevel: Int = 0,
    val accumulatedPoints: Double = 0.0
)