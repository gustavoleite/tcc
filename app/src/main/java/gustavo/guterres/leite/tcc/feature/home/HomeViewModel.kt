package gustavo.guterres.leite.tcc.feature.home

import android.content.Intent
import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.model.LevelItem
import gustavo.guterres.leite.tcc.data.entity.model.Student
import gustavo.guterres.leite.tcc.data.entity.model.StudentLevel
import gustavo.guterres.leite.tcc.data.repository.HomeRepository
import gustavo.guterres.leite.tcc.data.repository.StudentRepository
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import gustavo.guterres.leite.tcc.utils.extensions.toPoints
import gustavo.guterres.leite.tcc.utils.extensions.toPointsWithBreakLine
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.lang.IndexOutOfBoundsException

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val studentRepository: StudentRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val lastLevelUnlocked = ObservableInt()
    private val hitList = mutableListOf<ObservableField<StudentLevel>>()

    val loaderVisibility = ObservableInt(View.GONE)
    val levelList = MutableLiveData<List<LevelItem>>()
    val level = MutableLiveData<Level>()
    val requestInfo = MutableLiveData<String>()
    val logout = MutableLiveData<Unit>()
    val points = ObservableInt(0)
    val accumulatedPoints = ObservableField<String>("0\npontos")
    var authenticatedStudent: Student? = null

    fun setup() {
        points.addOnPropertyChangedCallback(onPointsChange())
        fetchStudentData()
    }

    fun onLogoutClick() {
        FirebaseAuth.getInstance().signOut()
        logout.value = Unit
    }

    private fun fetchStudentData() {
        studentRepository
            .fetchStudents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loaderVisibility.set(View.VISIBLE) }
            .subscribe(this::onFetchStudentsSuccess, this::onError)
            .addTo(compositeDisposable)
    }

    private fun fetchLevels() {
        homeRepository
            .fetchLevelsBrief()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { loaderVisibility.set(View.GONE) }
            .subscribe(this::setLevelBrief, this::onError)
            .addTo(compositeDisposable)

    }

    fun fetchLevelDetail(id: String) {
        homeRepository
            .fetchLevelDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loaderVisibility.set(View.VISIBLE) }
            .doFinally { loaderVisibility.set(View.GONE) }
            .subscribe(this::setLevel, this::onError)
            .addTo(compositeDisposable)
    }

    fun saveStudentData(student: Student) {
        authenticatedStudent = student
        updateUI(student)

        studentRepository
            .saveStudentData(student)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }

    private fun onFetchStudentsSuccess(students: List<Student>) {
        students.filter {
            it.id == FirebaseAuth.getInstance().currentUser?.uid
        }.map {
            authenticatedStudent = it
            updateUI(it)
            fetchLevels()
        }

        if (authenticatedStudent == null) {
            FirebaseAuth.getInstance().signOut()
            logout.value = Unit
        }
    }

    private fun updateUI(student: Student) {
        points.set(student.accumulatedPoints.toInt())
        lastLevelUnlocked.set(student.currentLevel)

        if (hitList.size > 0) {
            val levelId = level.value?.id?.toInt() ?: 0
            hitList[levelId].set(student.studentLevel[levelId])
            if (hitList.getOrNull(levelId + 1) == null && lastLevelUnlocked.get() == levelId + 1) {
                hitList[levelId + 1].set(StudentLevel((levelId + 1).toString()))
            }
        }
    }

    private fun onPointsChange(): Observable.OnPropertyChangedCallback {
        return object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                accumulatedPoints.set(points.get().toPointsWithBreakLine())
            }
        }
    }

    private fun setLevelBrief(response: List<Level>) {

        authenticatedStudent?.let { student ->
            student.studentLevel.map {
                hitList.add(ObservableField(it))
            }
        }

        levelList.value = response.map {
            LevelItem(
                it,
                -1,
                lastLevelUnlocked,
                getStudentLevel(it)
            )
        }
    }

    private fun getStudentLevel(it: Level): ObservableField<StudentLevel> {
        return try {
            hitList[it.id.toInt()]
        } catch (indexOutBounds: IndexOutOfBoundsException) {
            hitList.add(ObservableField())
            hitList[hitList.size - 1]
        }
    }

    private fun setLevel(response: Level) {
        level.value = response
    }

    private fun onError(throwable: Throwable) {
        requestInfo.value = resourceProvider.getString(R.string.home_request_error)
    }
}