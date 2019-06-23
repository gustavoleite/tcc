package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Content
import gustavo.guterres.leite.tcc.data.entity.output.ContentEntity

object ContentMapper {

    fun toContent(entity: ContentEntity) : Content {
        return with(entity) {
            Content(
                description,
                SpotlightMapper.toSpotlightList(spotlights)
            )
        }
    }
}