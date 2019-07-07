package gustavo.guterres.leite.tcc.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import gustavo.guterres.leite.tcc.data.entity.mapper.LevelMapper
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.output.LevelOutput
import io.reactivex.Single

class HomeRepositoryImpl(private val firebaseDatabase: FirebaseDatabase) : HomeRepository {

    override fun fetchLevelsBrief(): Single<List<Level>> {

        return Single.create { emitter ->

            firebaseDatabase
                .getReference(LEVEL_BRIEF_PATH)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val levelList = mutableListOf<Level>()

                        dataSnapshot.children.mapIndexed { index, response ->
                            response.getValue<LevelOutput>(LevelOutput::class.java)?.let { levelOutput ->
                                levelList.add(LevelMapper.toLevel(levelOutput.apply { id = index }))
                            }
                        }

                        emitter.onSuccess(levelList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }
                })
        }
    }

    override fun fetchLevelDetail(identifier: String): Single<Level> {

        return Single.create { emitter ->

            firebaseDatabase
                .getReference(LEVEL_PATH)
                .child(identifier)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dataSnapshot.getValue<LevelOutput>(LevelOutput::class.java)?.let { levelOutput ->
                            emitter.onSuccess(LevelMapper.toLevel(levelOutput.apply { id = identifier.toInt() }))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }
                })
        }
    }

    companion object {
        private const val LEVEL_BRIEF_PATH: String = "level-brief"
        private const val LEVEL_PATH: String = "level"
    }
}