package gustavo.guterres.leite.tcc.data.repository

import gustavo.guterres.leite.tcc.data.entity.model.Student
import io.reactivex.Single

interface StudentRepository {
    fun fetchStudents(): Single<List<Student>>
    fun saveStudentData(student: Student): Single<Boolean>
}
