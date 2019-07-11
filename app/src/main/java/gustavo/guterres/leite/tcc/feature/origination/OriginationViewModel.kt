package gustavo.guterres.leite.tcc.feature.origination

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.repository.OriginationRepository
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class OriginationViewModel(
    private val repository: OriginationRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val passwordConfirmation = ObservableField<String>()
    val loaderVisibility = ObservableInt(View.GONE)

    val message = MutableLiveData<String>()
    val navigation = MutableLiveData<Unit>()

    fun onCreateAccountClick() {
        if (isValidData()) {
            if (isValidPassword()) {
                repository
                    .createAccount(email.get().orEmpty(), password.get().orEmpty())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { loaderVisibility.set(View.VISIBLE) }
                    .doFinally { loaderVisibility.set(View.GONE) }
                    .subscribe(this::onCreatedAccountWithSuccess, this::onError)
                    .addTo(compositeDisposable)
            } else {
                message.value = resourceProvider.getString(R.string.origination_error_diff)
            }
        } else {
            message.value = resourceProvider.getString(R.string.origination_error_info)
        }
    }

    private fun onCreatedAccountWithSuccess(response: Boolean) {
        message.value = resourceProvider.getString(R.string.origination_request_success)
        navigation.value = Unit
    }

    private fun onError(throwable: Throwable) {
        message.value = resourceProvider.getString(R.string.origination_request_error)
    }

    private fun isValidData(): Boolean {
        return !email.get().isNullOrEmpty()
                && !password.get().isNullOrEmpty()
                && !passwordConfirmation.get().isNullOrEmpty()
    }

    private fun isValidPassword(): Boolean {
        return password.get().equals(passwordConfirmation.get())
    }
}