package gustavo.guterres.leite.tcc.feature.levelonboarding

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LevelOnboardingViewModel(private val totalScreens: Int) : ViewModel() {

    val okButtonVisibility = ObservableInt(View.GONE)
    val okClick = MutableLiveData<Unit>()
    val backClick = MutableLiveData<Unit>()
    val currentItem = MutableLiveData<Int>()

    init {
        currentItem.value = 0
        handleOkButtonVisibility(0)
    }

    fun onOkClick() {
        okClick.value = Unit
    }

    fun onCloseClick() {
        backClick.value = Unit
    }

    fun onBackClick() {
        if (currentItem.value ?: 0 > 0) {
            currentItem.postValue(currentItem.value?.minus(1))
        }
    }

    fun onNextClick() {
        if (currentItem.value ?: 0 < totalScreens.minus(1)) {
            currentItem.postValue(currentItem.value?.plus(1))
        }
    }

    fun handleOkButtonVisibility(currentScreen: Int) {
        if ((currentScreen + 1) == totalScreens) {
            okButtonVisibility.set(View.VISIBLE)
        } else {
            okButtonVisibility.set(View.GONE)
        }
    }
}