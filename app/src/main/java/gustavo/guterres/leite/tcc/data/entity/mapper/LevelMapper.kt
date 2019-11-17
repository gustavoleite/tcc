package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.output.LevelEntity
import gustavo.guterres.leite.tcc.data.entity.output.LevelOutput

object LevelMapper {

    fun toLevel(output: LevelOutput): Level {
        return with(output) {
            Level(
                id.toString(),
                name.orEmpty(),
                number.toString(),
                StepMapper.toList(steps),
                OnboardingMapper.toList(onboardings)
            )
        }
    }
}