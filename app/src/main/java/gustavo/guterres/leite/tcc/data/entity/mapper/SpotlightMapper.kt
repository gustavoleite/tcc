package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Spotlight
import gustavo.guterres.leite.tcc.data.entity.output.SpotlightEntity

object SpotlightMapper {

    fun toSpotlightList(entityList: List<SpotlightEntity>): List<Spotlight> {
        return entityList.map {
            toSpotlight(it)
        }
    }

    fun toSpotlight(entity: SpotlightEntity): Spotlight {
        return with(entity) {
            Spotlight(
                ImageMapper.toDrawableId(image) ?: R.drawable.ic_placeholder,
                value
            )
        }
    }
}