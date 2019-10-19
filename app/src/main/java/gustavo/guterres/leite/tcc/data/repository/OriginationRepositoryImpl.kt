package gustavo.guterres.leite.tcc.data.repository

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single

class OriginationRepositoryImpl(private val auth: FirebaseAuth) : OriginationRepository {

    override fun transformToEmailAndPasswordAccount(
        email: String,
        password: String
    ): Single<Boolean> {

        return Single.create { emitter ->

            val credential = EmailAuthProvider.getCredential(email, password)
            auth.currentUser?.linkWithCredential(credential)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(true)
                    } else {
                        emitter.onError(task.exception ?: Exception("Não foi possível vincular o email a conta anõnima."))
                    }
                }
        }
    }
}