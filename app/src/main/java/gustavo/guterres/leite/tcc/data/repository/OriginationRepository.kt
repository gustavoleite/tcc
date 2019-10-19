package gustavo.guterres.leite.tcc.data.repository

import io.reactivex.Single

interface OriginationRepository {
    fun transformToEmailAndPasswordAccount(email: String, password: String): Single<Boolean>
}