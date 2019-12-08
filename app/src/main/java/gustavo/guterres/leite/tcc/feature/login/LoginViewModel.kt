package gustavo.guterres.leite.tcc.feature.login

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.School
import gustavo.guterres.leite.tcc.data.entity.model.Student
import gustavo.guterres.leite.tcc.data.repository.LoginRepository
import gustavo.guterres.leite.tcc.data.repository.OriginationRepository
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val originationRepository: OriginationRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val password = ObservableField<String>()
    val loaderVisibility = ObservableInt(View.GONE)
    val contentVisibility = ObservableInt(View.GONE)

    val message = MutableLiveData<String>()
    val navigation = MutableLiveData<Unit>()
    val schools = MutableLiveData<List<String>>()
    val classrooms = MutableLiveData<List<String>>()
    val students = MutableLiveData<List<String>>()

    private lateinit var schoolListResponse: List<School>

    lateinit var selectedSchool: String
    lateinit var selectedClassroom: String
    var selectedStudent: Student = Student()

    fun fetchData() {
        loginRepository
            .fetchLoginOptions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                loaderVisibility.set(View.VISIBLE)
                contentVisibility.set(View.GONE)
            }
            .doFinally {
                loaderVisibility.set(View.GONE)
                contentVisibility.set(View.VISIBLE)
            }
            .subscribe(this::onFetchDataSuccess, this::onError)
            .addTo(compositeDisposable)
    }

    fun onLoginClick() {
        loaderVisibility.set(View.VISIBLE)

        var path = "school/"

        schoolListResponse
            .filter { it.name == selectedSchool }
            .flatMap {
                path = path.plus(it.id).plus("/classrooms/")
                it.classrooms
            }
            .filter { it.name == selectedClassroom }
            .flatMap {
                path = path.plus(it.id).plus("/students")
                it.students
            }
            .forEachIndexed { i, s ->
                if (s.name == selectedStudent.name) {
                    selectedStudent = s
                }
            }

        originationRepository
            .transformToEmailAndPasswordAccount(
                getEmail(),
                password.get().orEmpty(),
                selectedStudent,
                path
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { loaderVisibility.set(View.GONE) }
            .subscribe(this::onCreatedAccountWithSuccess, this::onCreateAccountError)
            .addTo(compositeDisposable)
    }

    private fun onCreatedAccountWithSuccess(response: Boolean) {
        signIn()
    }

    private fun onCreateAccountError(throwable: Throwable) {
        if (throwable is FirebaseAuthUserCollisionException) {
            signIn()
        } else {
            message.value = resourceProvider.getString(R.string.origination_request_error)
        }
    }

    private fun signIn() {
        loginRepository
            .signInWith(getEmail(), password.get().orEmpty())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loaderVisibility.set(View.VISIBLE) }
            .doFinally { loaderVisibility.set(View.GONE) }
            .subscribe(this::onSignedWithSuccess, this::onError)
            .addTo(compositeDisposable)
    }

    fun loadClassroomsOptions(newSelectedSchool: String) {
        schoolListResponse.filter {
            it.name == newSelectedSchool
        }.map {
            it.classrooms.map {
                it.name
            }.also {
                this.classrooms.value = it
                this.selectedSchool = newSelectedSchool
            }
        }
    }

    fun loadStudentOptions(newSelectedClassroom: String) {
        schoolListResponse.filter {
            it.name == selectedSchool
        }.map {
            it.classrooms.filter {
                it.name == newSelectedClassroom
            }.map {
                it.students.map {
                    it.name
                }.also {
                    this.students.value = it
                    this.selectedClassroom = newSelectedClassroom
                }
            }
        }
    }

    private fun onSignedWithSuccess(response: FirebaseUser) {
        navigation.value = Unit
    }

    private fun onError(throwable: Throwable) {
        message.value = resourceProvider.getString(R.string.login_request_error)
    }

    private fun onFetchDataSuccess(schools: List<School>) {
        schoolListResponse = schools
        schools.map {
            it.name
        }.also {
            this.schools.value = it
        }
    }

    private fun getEmail(): String {
        return "$selectedSchool.$selectedClassroom.${selectedStudent.name}@email.com".replace(" ", "")
    }
}
