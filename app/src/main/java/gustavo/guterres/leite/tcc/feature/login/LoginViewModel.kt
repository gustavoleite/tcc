package gustavo.guterres.leite.tcc.feature.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.repository.LoginRepository
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class LoginViewModel(
    private val repository: LoginRepository,
    private val resourceProvider: ResourceProvider) : BaseViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()

    val message = MutableLiveData<String>()
    val navigation = MutableLiveData<LoginNavigation>()

    fun onLoginClick() {
        repository
            .signInWith(email.get().orEmpty(), password.get().orEmpty())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onSignedWithSuccess, this::onError)
            .addTo(compositeDisposable)
    }

    private fun onSignedWithSuccess(response: FirebaseUser) {
        navigation.value = LoginNavigation.LOGIN
    }

    private fun onError(throwable: Throwable) {
        message.value = resourceProvider.getString(R.string.login_request_error)
    }

    fun onCreateAccountLogin() {
        navigation.value = LoginNavigation.CREATE_ACCCOUNT
    }
}