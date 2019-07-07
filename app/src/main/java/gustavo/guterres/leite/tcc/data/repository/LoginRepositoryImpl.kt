package gustavo.guterres.leite.tcc.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single

class LoginRepositoryImpl(private val auth: FirebaseAuth) : LoginRepository {

    override fun signInWith(email: String, password: String): Single<FirebaseUser> {

        return Single.create { emitter ->

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.let {
                            emitter.onSuccess(it)
                        }
                    } else {
                        emitter.onError(task.exception ?: Exception("Generic request message"))
                    }
                }
        }
    }
}