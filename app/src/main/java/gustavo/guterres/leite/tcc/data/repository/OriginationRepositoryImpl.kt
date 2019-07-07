package gustavo.guterres.leite.tcc.data.repository

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single


class OriginationRepositoryImpl() : OriginationRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun createAccount(email: String, password: String): Single<Boolean> {

        return Single.create { emitter ->

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(true)
                    } else {
                        emitter.onError(task.exception ?: Exception("Generic request message"))
                    }
                }
        }
    }
}