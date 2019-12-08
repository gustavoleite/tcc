package gustavo.guterres.leite.tcc.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import gustavo.guterres.leite.tcc.data.entity.mapper.SchoolMapper
import gustavo.guterres.leite.tcc.data.entity.mapper.StudentMapper
import gustavo.guterres.leite.tcc.data.entity.model.School
import gustavo.guterres.leite.tcc.data.entity.model.Student
import gustavo.guterres.leite.tcc.data.entity.output.SchoolOutput
import gustavo.guterres.leite.tcc.data.entity.output.StudentOutput
import io.reactivex.Single
import io.reactivex.SingleEmitter

class LoginRepositoryImpl(private val auth: FirebaseAuth, private val database: FirebaseDatabase) :
    LoginRepository {

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

    override fun fetchLoginOptions(): Single<List<School>> {

        return Single.create { emitter ->

            auth.signInAnonymously()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.currentUser?.let {
                            fetchStudents(emitter)
                        }
                    } else {
                        emitter.onError(task.exception ?: Exception("Generic request message"))
                    }
                }
        }
    }

    private fun fetchStudents(emitter: SingleEmitter<List<School>>) {
        database
            .getReference(STUDENTS_PATH)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val studentList = mutableListOf<Student>()

                    dataSnapshot.children.map { response ->
                        response.getValue<StudentOutput>(StudentOutput::class.java)
                            ?.let { studentOutput ->
                                // classRoom!!.setId(ds.key)
                                studentList.add(StudentMapper.toStudent(studentOutput.apply {
                                    id = response.key.orEmpty()
                                }))
                            }
                    }

                    fetchSchools(emitter, studentList)
                }

                override fun onCancelled(error: DatabaseError) {
                    emitter.onError(error.toException())
                }
            })
    }

    private fun fetchSchools(emitter: SingleEmitter<List<School>>, students: List<Student>) {
        database
            .getReference(SCHOOLS_PATH)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val schoolsList = mutableListOf<School>()

                    dataSnapshot.children.map { response ->
                        response.getValue<SchoolOutput>(SchoolOutput::class.java)
                            ?.let { schoolsOutput ->
                                schoolsList.add(SchoolMapper.toSchool(schoolsOutput.apply { id = response.key.orEmpty() }, students))
                            }
                    }

                    emitter.onSuccess(schoolsList)
                }

                override fun onCancelled(error: DatabaseError) {
                    emitter.onError(error.toException())
                }
            })
    }

    companion object {
        private const val STUDENTS_PATH = "students"
        private const val SCHOOLS_PATH = "school"
    }
}
