package gustavo.guterres.leite.tcc.feature.origination

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OriginationViewModel : ViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val passwordConfirmation = ObservableField<String>()

    val invalidData = MutableLiveData<Unit>()

    fun onCreateAccountClick() {
        if (isValidData()) {
            //cadastrar no firebase
        } else {
            invalidData.value = Unit
        }
    }

    private fun isValidData() : Boolean {
        return !email.get().isNullOrEmpty()
                && !password.get().isNullOrEmpty()
                && !passwordConfirmation.get().isNullOrEmpty()
    }
}