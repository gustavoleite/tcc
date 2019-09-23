package gustavo.guterres.leite.tcc.data.entity.output

data class SchoolOutput(
    val name: String = "",
    val classrooms : List<ClassroomOutput> = emptyList()
)