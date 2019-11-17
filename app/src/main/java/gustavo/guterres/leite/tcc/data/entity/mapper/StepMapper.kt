package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Step
import gustavo.guterres.leite.tcc.data.entity.output.StepOutput

object StepMapper {

    fun toList(outputList: List<StepOutput>?): List<Step>? {
        return outputList?.map {
            toStep(it)
        }
    }

    fun toStep(entity: StepOutput): Step {
        return with(entity) {
            Step(
                id.toString(),
                points ?: 0.0,
                ContentMapper.toContent(content),
                ActionMapper.toList(actions),
                rightActionIdList ?: arrayListOf()
            )
        }
    }
}