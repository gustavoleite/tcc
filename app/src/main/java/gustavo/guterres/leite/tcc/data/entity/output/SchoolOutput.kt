package gustavo.guterres.leite.tcc.data.entity.output

import androidx.annotation.Keep

@Keep
data class SchoolOutput(
    var id : String = "",
    val name: String = "",
    val classrooms : List<ClassroomOutput> = emptyList()
)