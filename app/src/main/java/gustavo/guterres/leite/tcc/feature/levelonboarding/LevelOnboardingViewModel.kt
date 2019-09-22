package gustavo.guterres.leite.tcc.feature.levelonboarding

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LevelOnboardingViewModel(private val totalScreens: Int) : ViewModel() {

    val okButtonVisibility = ObservableInt(View.GONE)
    val okClick = MutableLiveData<Unit>()

    init {
        handleOkButtonVisibility(0)
    }

    fun onOkClick() {
        okClick.value = Unit
    }

    fun handleOkButtonVisibility(currentScreen: Int) {
        if ((currentScreen + 1) == totalScreens) {
            okButtonVisibility.set(View.VISIBLE)
        } else {
            okButtonVisibility.set(View.GONE)
        }
    }
}