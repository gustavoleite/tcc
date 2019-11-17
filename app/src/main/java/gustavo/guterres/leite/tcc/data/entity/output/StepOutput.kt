package gustavo.guterres.leite.tcc.data.entity.output

import androidx.annotation.Keep

@Keep
data class StepOutput(

    val id: Int? = null,

    var points: Double? = null,

    val content: ContentOutput? = null,

    var actions: List<ActionOutput>? = null,

    var rightActionIdList : List<String>? = null
)