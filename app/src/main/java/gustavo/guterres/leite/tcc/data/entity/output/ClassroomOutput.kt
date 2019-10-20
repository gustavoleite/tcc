package gustavo.guterres.leite.tcc.data.entity.output

data class ClassroomOutput(
    var id: String = "",
    val name: String = "",
    val students: List<String> = emptyList()
)