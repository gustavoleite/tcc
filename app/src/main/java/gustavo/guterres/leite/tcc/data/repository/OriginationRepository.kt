package gustavo.guterres.leite.tcc.data.repository

import gustavo.guterres.leite.tcc.data.entity.model.Student
import io.reactivex.Single

interface OriginationRepository {

    fun transformToEmailAndPasswordAccount(
        email: String,
        password: String,
        student: Student,
        path: String
    ): Single<Boolean>
}