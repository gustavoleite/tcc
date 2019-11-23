package gustavo.guterres.leite.tcc.data.repository

import gustavo.guterres.leite.tcc.data.entity.model.Level
import io.reactivex.Single

interface HomeRepository {
    fun fetchLevelsBrief(): Single<List<Level>>
    fun fetchLevelDetail(identifier: String): Single<Level>
}
