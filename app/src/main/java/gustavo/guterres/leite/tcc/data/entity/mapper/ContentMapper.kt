package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Content
import gustavo.guterres.leite.tcc.data.entity.output.ContentEntity
import gustavo.guterres.leite.tcc.data.entity.output.ContentOutput
import java.lang.Exception

object ContentMapper {

    fun toContent(entity: ContentEntity): Content {
        return with(entity) {
            Content(
                description,
                SpotlightMapper.toSpotlightList(spotlights)
            )
        }
    }

    fun toContent(output: ContentOutput?): Content {
        return output?.let {
            with(output) {
                Content(
                    description ?: "",
                    SpotlightMapper.toList(spotlights)
                )
            }
        } ?: throw Exception("Content not found")
    }
}
