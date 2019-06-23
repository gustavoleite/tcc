package gustavo.guterres.leite.tcc.data.repository

import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.output.LevelEntity
import io.reactivex.Completable
import io.reactivex.Single

interface LevelRepository {

    fun getLevels(id: String) : Single<List<Level>>

    //@TODO Helper method, future will be excluded
    fun saveLevel(levelEntity: LevelEntity) : Completable
}