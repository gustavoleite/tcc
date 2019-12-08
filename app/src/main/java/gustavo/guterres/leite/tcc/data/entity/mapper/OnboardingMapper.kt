package gustavo.guterres.leite.tcc.data.entity.mapper

import gustavo.guterres.leite.tcc.data.entity.model.Onboarding
import gustavo.guterres.leite.tcc.data.entity.output.OnboardingOutput

object OnboardingMapper {

    fun toList(outputList: List<OnboardingOutput>?): List<Onboarding>? {
        return outputList?.map {
            toOnboarding(it)
        }
    }

    fun toOnboarding(output: OnboardingOutput): Onboarding {
        return with(output) {
            Onboarding(
                title.orEmpty(),
                message.orEmpty()
            )
        }
    }
}
