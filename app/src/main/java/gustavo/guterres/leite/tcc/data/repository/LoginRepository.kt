package gustavo.guterres.leite.tcc.data.repository

import com.google.firebase.auth.FirebaseUser
import gustavo.guterres.leite.tcc.data.entity.model.School
import io.reactivex.Single

interface LoginRepository {
    fun signInWith(email: String, password: String): Single<FirebaseUser>
    fun fetchLoginOptions(): Single<List<School>>
}
