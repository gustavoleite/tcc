package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Step
import gustavo.guterres.leite.tcc.data.entity.output.StepEntity
import gustavo.guterres.leite.tcc.data.entity.output.StepOutput
import java.lang.Exception

object StepMapper {

    fun toStepList(entityList: List<StepEntity>): List<Step> {
        return entityList.map {
            toStep(it)
        }
    }

    fun toList(outputList: List<StepOutput>?): List<Step>? {
        return outputList?.map {
            toStep(it)
        }
    }

    fun toStep(entity: StepEntity): Step {
        return with(entity) {
            Step(
                id.toString(),
                points,
                ContentMapper.toContent(content),
                ActionMapper.toActionList(actions),
                rightActionId.toString()
            )
        }
    }

    fun toStep(entity: StepOutput): Step {
        return with(entity) {
            Step(
                id.toString(),
                points ?: 0.0,
                ContentMapper.toContent(content),
                ActionMapper.toList(actions),
                rightActionId.toString()
            )
        }
    }
}