package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.output.LevelEntity

object LevelMapper {

    fun toLevelList(entityList: List<LevelEntity>): List<Level> {
        return entityList.map {
            toLevel(it)
        }
    }

    fun toLevel(entity: LevelEntity): Level {
        return with(entity) {
            Level(
                id.toString(),
                name,
                null,
                StepMapper.toStepList(steps)
            )
        }
    }
}