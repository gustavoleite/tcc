package gustavo.guterres.leite.tcc.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import gustavo.guterres.leite.tcc.data.entity.mapper.StudentMapper
import gustavo.guterres.leite.tcc.data.entity.model.Student
import gustavo.guterres.leite.tcc.data.entity.output.StudentOutput
import io.reactivex.Single

class StudentRepositoryImpl(private val database: FirebaseDatabase, private val auth: FirebaseAuth) : StudentRepository {

    override fun fetchStudents(): Single<List<Student>> {

        return Single.create { emitter ->

            database
                .getReference(STUDENTS_PATH)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val studentList = mutableListOf<Student>()

                        dataSnapshot.children.map { response ->
                            response.getValue<StudentOutput>(StudentOutput::class.java)
                                ?.let { studentOutput ->

                                    studentList.add(StudentMapper.toStudent(studentOutput.apply {
                                        id = response.key.orEmpty()
                                    }))
                                }
                        }

                        emitter.onSuccess(studentList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }
                })
        }
    }

    override fun saveStudentData(student: Student): Single<Boolean> {
        return Single.create { emitter ->

            database
                .getReference(STUDENTS_PATH)
                .child(auth.currentUser?.uid.orEmpty())
                .ref
                .setValue(student)
                .addOnSuccessListener {
                    emitter.onSuccess(true)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    companion object {
        private const val STUDENTS_PATH = "students"
    }
}