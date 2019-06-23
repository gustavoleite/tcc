package gustavo.guterres.leite.tcc.feature.step

import androidx.databinding.ObservableField
import gustavo.guterres.leite.tcc.data.entity.model.Action
import gustavo.guterres.leite.tcc.data.entity.model.Spotlight
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import gustavo.guterres.leite.tcc.data.entity.model.Step

class StepViewModel : BaseViewModel() {

    val question = ObservableField<String>()
    val spotlightList = ObservableField<List<Spotlight>>()
    val actionList = ObservableField<List<Action>>()

    fun setup(step: Step) {
        with(step) {
            question.set(content.description)
            spotlightList.set(content.spotlights)
            actionList.set(actions)
        }
    }
}