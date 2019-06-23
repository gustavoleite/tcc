package gustavo.guterres.leite.tcc.feature.step

import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Action
import gustavo.guterres.leite.tcc.data.entity.model.Spotlight
import gustavo.guterres.leite.tcc.data.repository.LevelRepository
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import android.util.Log
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.output.*

class StepViewModel(private val repository: LevelRepository) : BaseViewModel() {

    val contentList: List<Spotlight> = listOf(
        Spotlight(R.drawable.ic_hundred_reais, 23.4),
        Spotlight(R.drawable.ic_hundred_reais),
        Spotlight(R.drawable.ic_hundred_reais, 23.4)
    )

    val actionList: List<Action> = listOf(
        Action("1", text = "TEXT"),
        Action("2", text = "TEXT"),
        Action("2", text = "TEXT"),
        Action("2", text = "TEXT"),
        Action("2", text = "TEXT"),
        Action("3", text = "TEXT")
    )

    init {
        fillData()
    }

    fun fillData() {
        repository
            .saveLevel(getDataMock())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onFillDataSuccess, this::onError)
            .addTo(compositeDisposable)
    }

    fun getDataMock(): LevelEntity {
        return LevelEntity(
            1,
            "As notas",
            listOf(
                StepEntity(
                    1,
                    45.7,
                    ContentEntity(
                        1,
                        "Quantos reais temos somando estas notas?",
                        listOf(
                            SpotlightEntity(
                                1,
                                null,
                                "CEM_REAIS"
                            )
                        )
                    ),
                    listOf(
                        ActionEntity(
                            1,
                            "50,00",
                            null
                        )
                    )
                )
            )
        )
    }

    private fun onFillDataSuccess() {
        Log.d("Dados inseridos", "Com sucesso!")
        fetchData()
    }

    fun fetchData() {
        repository
            .getLevels("1")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onLevelsFetched, this::onError)
            .addTo(compositeDisposable)
    }

    private fun onLevelsFetched(levels: List<Level>) {
        Log.d("Dados encontrados:", levels.toString())
    }

    private fun onError(throwable: Throwable) {
        Log.d("Errrooooouuuu!", throwable.message)
    }
}