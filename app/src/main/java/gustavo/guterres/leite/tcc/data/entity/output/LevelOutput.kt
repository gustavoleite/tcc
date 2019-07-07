package gustavo.guterres.leite.tcc.data.entity.output

import gustavo.guterres.leite.tcc.data.entity.model.Step

data class LevelOutput(

    var id: Int? = null,

    val name: String? = null,

    val number: Long? = null,

    val steps: List<Step>? = null
)