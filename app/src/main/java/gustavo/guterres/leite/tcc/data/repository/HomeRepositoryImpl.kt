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
                .getReference(LEVEL_BRIEF)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val levelList = mutableListOf<Level>()

                        dataSnapshot.children.mapIndexed { index, response ->
                            response.getValue<LevelOutput>(LevelOutput::class.java)?.let { level ->
                                levelList.add(LevelMapper.toLevel(level.apply { id = index }))
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

    override fun fetchLevelDetail(id: String): Single<Level> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private const val LEVEL_BRIEF: String = "level-brief"
    }
}