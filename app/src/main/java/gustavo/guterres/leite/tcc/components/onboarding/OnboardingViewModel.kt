package gustavo.guterres.leite.tcc.components.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gustavo.guterres.leite.tcc.data.entity.model.Onboarding

class OnboardingViewModel(onboarding: Onboarding) : ViewModel() {

    val title = onboarding.title
    val message = onboarding.message
    val tts = MutableLiveData<String>()

    fun onSpeakClick() {
        tts.value = title.plus(" ").plus(message)
    }
}
