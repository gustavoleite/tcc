package gustavo.guterres.leite.tcc.data.entity.output

import androidx.annotation.Keep

@Keep
data class ClassroomOutput(
    var id: String = "",
    val name: String = "",
    val students: List<String> = emptyList()
)