package gustavo.guterres.leite.tcc.feature.home

import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.model.Student
import gustavo.guterres.leite.tcc.data.repository.HomeRepository
import gustavo.guterres.leite.tcc.data.repository.StudentRepository
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import gustavo.guterres.leite.tcc.utils.extensions.toBrCurrency
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val studentRepository: StudentRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val loaderVisibility = ObservableInt(View.GONE)

    val levelList = MutableLiveData<List<Level>>()
    val level = MutableLiveData<Level>()
    val requestInfo = MutableLiveData<String>()
    val points = ObservableDouble(0.00)
    val accumulatedPoints = ObservableField<String>("R$ 0,00")
    var authenticatedStudent: Student? = null

    fun setup() {
        points.addOnPropertyChangedCallback(onPointsChange())
        fetchLevels()
        fetchStudentData()
    }

    private fun fetchLevels() {
        homeRepository
            .fetchLevelsBrief()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loaderVisibility.set(View.VISIBLE) }
            .doFinally { loaderVisibility.set(View.GONE) }
            .subscribe(this::setLevelBrief, this::onError)
            .addTo(compositeDisposable)

    }

    private fun fetchStudentData() {
        studentRepository
            .fetchStudents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onFetchStudentsSuccess, this::onError)
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
        points.set(student.accumulatedPoints)

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
            points.set(it.accumulatedPoints)
        }
    }

    private fun onPointsChange(): Observable.OnPropertyChangedCallback {
        return object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                accumulatedPoints.set(points.get().toBrCurrency())
            }
        }
    }

    private fun setLevelBrief(response: List<Level>) {
        levelList.value = response
    }

    private fun setLevel(response: Level) {
        level.value = response
    }

    private fun onError(throwable: Throwable) {
        requestInfo.value = resourceProvider.getString(R.string.home_request_error)
    }
}