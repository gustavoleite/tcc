package gustavo.guterres.leite.tcc.data.repository

import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single

interface LoginRepository {
    fun signInWith(email: String, password: String): Single<FirebaseUser>
}