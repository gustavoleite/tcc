package gustavo.guterres.leite.tcc.data.entity.model

data class Step(

    val id: String,

    var points: Double,

    val content: Content,

    var actions: List<Action>
)