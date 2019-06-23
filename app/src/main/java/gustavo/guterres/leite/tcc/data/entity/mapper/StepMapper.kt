package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Step
import gustavo.guterres.leite.tcc.data.entity.output.StepEntity

object StepMapper {

    fun toStepList(entityList: List<StepEntity>): List<Step> {
        return entityList.map {
            toStep(it)
        }
    }

    fun toStep(entity: StepEntity) : Step {
        return with(entity) {
            Step(
                id.toString(),
                points,
                ContentMapper.toContent(content),
                ActionMapper.toActionList(actions)
            )
        }
    }
}