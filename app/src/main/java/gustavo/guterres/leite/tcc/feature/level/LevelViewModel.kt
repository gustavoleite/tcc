package gustavo.guterres.leite.tcc.feature.level

import androidx.databinding.Observable
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.repository.LevelRepository
import gustavo.guterres.leite.tcc.data.repository.StudentRepository
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import gustavo.guterres.leite.tcc.utils.extensions.toBrCurrency

class LevelViewModel(
    private val repository: LevelRepository,
    private val studentRepository: StudentRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val close = MutableLiveData<Unit>()

    val currentStep = ObservableInt()
    val totalStep = ObservableInt()
    val progressInfo = ObservableField<String>()
    val points = ObservableDouble(0.00)
    val levelAculametedPoints = ObservableField<String>("R$ 0,00")

    var hits = 0
    var mistakes = 0

    init {
        points.addOnPropertyChangedCallback(onPointsChange())
        currentStep.addOnPropertyChangedCallback(onCurrentStepChange())
    }

    fun setup(level: Level) {
        this.totalStep.set(level.steps?.size ?: 0)
        this.currentStep.set(1)
    }

    fun onCloseClick() {
        close.value = Unit
    }

    fun setUserAnswer(isRightAnswer: Boolean, stepPoints: Double) {
        if (isRightAnswer) {
            this.points.set(this.points.get() + stepPoints)
            hits++
        } else {
            mistakes++
        }
    }

    fun updateStudentUser() {

    }

    private fun onCurrentStepChange(): Observable.OnPropertyChangedCallback {
        return object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                progressInfo.set(updatedProgressInfo())
            }
        }
    }

    private fun onPointsChange(): Observable.OnPropertyChangedCallback {
        return object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                levelAculametedPoints.set(points.get().toBrCurrency())
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