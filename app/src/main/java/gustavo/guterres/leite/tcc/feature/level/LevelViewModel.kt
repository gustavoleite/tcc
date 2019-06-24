package gustavo.guterres.leite.tcc.feature.level

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.output.*
import gustavo.guterres.leite.tcc.data.repository.LevelRepository
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class LevelViewModel(
    private val repository: LevelRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val levels = MutableLiveData<List<Level>>()

    val currentStep = ObservableInt()
    val totalStep = ObservableInt()
    val progressInfo = ObservableField<String>()
    val close = MutableLiveData<Unit>()

    init {
        fillData()

        currentStep.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                progressInfo.set(
                    resourceProvider.getString(
                        R.string.level_progress_bar_info,
                        currentStep.get(),
                        totalStep.get()
                    )
                )
            }
        })
    }

    fun onCloseClick() {
        close.value = Unit
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
                                "CINQUENTA_REAIS"
                            ),
                            SpotlightEntity(
                                2,
                                null,
                                "CINCO_REAIS"
                            ),
                            SpotlightEntity(
                                3,
                                null,
                                "DOIS_REAIS"
                            )
                        )
                    ),
                    listOf(
                        ActionEntity(
                            1,
                            "R$ 55,00",
                            null
                        ),
                        ActionEntity(
                            2,
                            "R$ 52,00",
                            null
                        ),
                        ActionEntity(
                            3,
                            "R$ 57,00",
                            null
                        )
                    ),
                    3
                ),
                StepEntity(
                    2,
                    45.7,
                    ContentEntity(
                        2,
                        "Identifique o valor da nota",
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
                            "Cinquenta\nreais",
                            null
                        ),
                        ActionEntity(
                            2,
                            "Cem\nreais",
                            null
                        ),
                        ActionEntity(
                            3,
                            "Vinte\nreais",
                            null
                        )
                    ),
                    2
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
        this.levels.value = levels
        this.totalStep.set(levels.first().steps.size)
        this.currentStep.set(1)
        Log.d("Dados encontrados:", levels.toString())
    }

    private fun onError(throwable: Throwable) {
        Log.d("Errrooooouuuu!", throwable.message)
    }
}