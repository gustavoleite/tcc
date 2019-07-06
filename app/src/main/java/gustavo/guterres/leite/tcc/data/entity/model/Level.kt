package gustavo.guterres.leite.tcc.data.entity.model

data class Level(

    val id: String,

    val name: String,

    val number: String? = null,

    val steps: List<Step>? = null
)