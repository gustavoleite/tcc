package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Spotlight
import gustavo.guterres.leite.tcc.data.entity.output.SpotlightEntity
import gustavo.guterres.leite.tcc.data.entity.output.SpotlightOutput

object SpotlightMapper {

    fun toSpotlightList(entityList: List<SpotlightEntity>): List<Spotlight> {
        return entityList.map {
            toSpotlight(it)
        }
    }

    fun toList(outputList: List<SpotlightOutput>?): List<Spotlight> {
        return outputList?.map {
            toSpotlight(it)
        } ?: arrayListOf()
    }

    fun toSpotlight(entity: SpotlightEntity): Spotlight {
        return with(entity) {
            Spotlight(
                ImageMapper.toDrawableId(image) ?: R.drawable.ic_placeholder,
                value
            )
        }
    }

    fun toSpotlight(output: SpotlightOutput): Spotlight {
        return with(output) {
            Spotlight(
                ImageMapper.toDrawableId(image) ?: R.drawable.ic_placeholder,
                value
            )
        }
    }
}
