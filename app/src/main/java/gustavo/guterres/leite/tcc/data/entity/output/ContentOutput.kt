package gustavo.guterres.leite.tcc.data.entity.output

import androidx.annotation.Keep

@Keep
data class ContentOutput(

    var description: String? = null,

    var spotlights: List<SpotlightOutput>? = null
)