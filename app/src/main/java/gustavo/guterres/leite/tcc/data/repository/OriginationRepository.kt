package gustavo.guterres.leite.tcc.data.repository

import io.reactivex.Single

interface OriginationRepository {
    fun createAccount(email: String, password: String): Single<Boolean>
}