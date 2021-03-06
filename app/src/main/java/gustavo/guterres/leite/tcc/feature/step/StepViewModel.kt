package gustavo.guterres.leite.tcc.feature.step

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import gustavo.guterres.leite.tcc.data.entity.model.Action
import gustavo.guterres.leite.tcc.data.entity.model.Spotlight
import gustavo.guterres.leite.tcc.data.entity.model.Step
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel

class StepViewModel : BaseViewModel() {

    val question = ObservableField<String>()
    val spotlightList = ObservableField<List<Spotlight>>()
    val actionList = ObservableField<List<Action>>()

    val isRightAnswer = MutableLiveData<Boolean>()

    lateinit var expectedActionId: String

    fun setData(step: Step) {
        with(step) {
            question.set(content.description)
            spotlightList.set(content.spotlights)
            actionList.set(actions)
            expectedActionId = rightActionId
        }
    }

    fun onActionSelected(action: Action) {
        isRightAnswer.value = isExpected(action)
    }

    private fun isExpected(action: Action) = (action.id == expectedActionId)
}
