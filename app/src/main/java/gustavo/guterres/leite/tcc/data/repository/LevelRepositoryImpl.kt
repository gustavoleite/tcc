package gustavo.guterres.leite.tcc.data.repository

import gustavo.guterres.leite.tcc.data.entity.mapper.LevelMapper
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.output.LevelEntity
import gustavo.guterres.leite.tcc.data.room.LevelDao
import io.reactivex.Completable
import io.reactivex.Single

class LevelRepositoryImpl(private val levelDao: LevelDao) : LevelRepository {

    override fun getLevels(id: String): Single<List<Level>> {
        return levelDao
            .getAll()
            .map { LevelMapper.toLevelList(it) }
    }

    override fun saveLevel(levelEntity: LevelEntity) : Completable {
        return levelDao
            .insert(levelEntity)
    }
}