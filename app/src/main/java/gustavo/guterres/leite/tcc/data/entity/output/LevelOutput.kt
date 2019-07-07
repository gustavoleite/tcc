package gustavo.guterres.leite.tcc.data.entity.output

data class LevelOutput(

    var id: Int? = null,

    val name: String? = null,

    val number: Long? = null,

    val steps: List<StepOutput>? = null
)