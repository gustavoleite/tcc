package gustavo.guterres.leite.tcc.feature.level

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.repository.LevelRepository
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider

class LevelViewModel(
    private val repository: LevelRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val close = MutableLiveData<Unit>()

    val currentStep = ObservableInt()
    val totalStep = ObservableInt()
    val progressInfo = ObservableField<String>()

    init {
        currentStep.addOnPropertyChangedCallback(onCurrentStepChange())
    }

    fun setup(level: Level) {
        this.totalStep.set(level.steps?.size ?: 0)
        this.currentStep.set(1)
    }

    fun onCloseClick() {
        close.value = Unit
    }

    private fun onCurrentStepChange(): Observable.OnPropertyChangedCallback {
        return object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                progressInfo.set(updatedProgressInfo())
            }
        }
    }

    private fun updatedProgressInfo(): String {
        return resourceProvider.getString(
            R.string.level_progress_bar_info,
            currentStep.get(),
            totalStep.get()
        )
    }
}