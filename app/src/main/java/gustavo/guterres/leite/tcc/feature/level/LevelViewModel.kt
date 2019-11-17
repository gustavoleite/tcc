package gustavo.guterres.leite.tcc.feature.level

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.PlayLevel
import gustavo.guterres.leite.tcc.data.entity.model.Student
import gustavo.guterres.leite.tcc.data.entity.model.StudentLevel
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import gustavo.guterres.leite.tcc.utils.extensions.Event
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import gustavo.guterres.leite.tcc.utils.extensions.toPoints

class LevelViewModel(
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val close = MutableLiveData<Unit>()
    val student = MutableLiveData<Event<Student>>()
    lateinit var playLevel: PlayLevel

    val currentStep = ObservableInt()
    val totalStep = ObservableInt()
    val progressInfo = ObservableField<String>()
    val points = ObservableInt(0)
    val levelAccumulatedPoints = ObservableField<String>("0 pontos")

    var levelHits = 0
    var levelMistakes = 0

    init {
        points.addOnPropertyChangedCallback(onPointsChange())
        currentStep.addOnPropertyChangedCallback(onCurrentStepChange())
    }

    fun setup(playLevel: PlayLevel) {
        this.totalStep.set(playLevel.level.steps?.size ?: 0)
        this.currentStep.set(1)
        this.playLevel = playLevel
    }

    fun onCloseClick() {
        close.value = Unit
    }

    fun setUserAnswer(isRightAnswer: Boolean, stepPoints: Int) {
        if (isRightAnswer) {
            this.points.set(this.points.get() + stepPoints)
            levelHits++
        } else {
            levelMistakes++
        }
    }

    fun levelUp() = levelHits >= 3 && !hasWonCurrentLevel() && !hasPlayNextLevels()

    fun hasWonCurrentLevel(): Boolean {
        var won = false

        playLevel
            .student
            .studentLevel
            .filter { it.id == playLevel.level.id }
            .firstOrNull()?.let {
                won = it.hits >= 3
            }

        return won
    }

    fun hasPlayNextLevels() : Boolean {
        return playLevel.student.currentLevel > playLevel.level.id.toInt()
    }

    fun updateStudentUser() {
        val newStudentData = playLevel.student.apply {

            if (notPlayCurrentLevelYet(studentLevel)) {
                studentLevel.add(StudentLevel(playLevel.level.id, 0, 0))
            }

            accumulatedPoints += points.get()

            studentLevel
                .filter { it.id == playLevel.level.id }
                .map {
                    it.hits = levelHits
                    it.mistakes = levelMistakes

                    if (it.hits >= 3 && playLevel.nextLevelId.toInt() > currentLevel) {
                        currentLevel++
                    }
                }
        }

        student.postValue(Event(newStudentData))
    }

    private fun notPlayCurrentLevelYet(studentLevel: List<StudentLevel>): Boolean {
        return studentLevel
            .filter { it.id == playLevel.level.id }
            .isEmpty()
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
                levelAccumulatedPoints.set(points.get().toPoints())
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