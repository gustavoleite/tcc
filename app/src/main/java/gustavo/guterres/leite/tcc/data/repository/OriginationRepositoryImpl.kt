package gustavo.guterres.leite.tcc.data.repository

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import gustavo.guterres.leite.tcc.data.entity.model.Student
import io.reactivex.Single
import io.reactivex.SingleEmitter

class OriginationRepositoryImpl(private val auth: FirebaseAuth, private val database: FirebaseDatabase) : OriginationRepository {

    override fun transformToEmailAndPasswordAccount(
        email: String,
        password: String,
        student: Student,
        path: String
    ): Single<Boolean> {

        return Single.create { emitter ->

            val credential = EmailAuthProvider.getCredential(email, password)
            auth.currentUser?.linkWithCredential(credential)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        replaceStudentId(emitter, student, path)
                        emitter.onSuccess(true)
                    } else {
                        emitter.onError(task.exception ?: Exception("Não foi possível vincular o email a conta anõnima."))
                    }
                }
        }
    }

    private fun replaceStudentId(emitter: SingleEmitter<Boolean>, student: Student, path: String) {
        val newId = auth.currentUser?.uid

        database
            .reference
            .child(STUDENTS_PATH)
            .child(student.id)
            .removeValue()
            .addOnSuccessListener {
                database
                    .reference
                    .child(STUDENTS_PATH)
                    .child(newId.orEmpty())
                    .setValue(student.copy(id = newId.orEmpty()))
                    .addOnSuccessListener {
                        updateStudantIdInSchoolTable(path, student, newId, emitter)
                    }
                    .addOnFailureListener {
                        emitter.onError(it)
                    }
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

    private fun updateStudantIdInSchoolTable(
        path: String,
        student: Student,
        newId: String?,
        emitter: SingleEmitter<Boolean>
    ) {
        database
            .reference
            .child(path)
            .orderByValue()
            .equalTo(student.id)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (postSnapshot in dataSnapshot.children) {
                        postSnapshot.ref.setValue(newId.orEmpty())
                    }
                    emitter.onSuccess(true)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    emitter.onError(databaseError.toException())
                }
            })
    }

    companion object {
        private const val STUDENTS_PATH = "students"
    }
}