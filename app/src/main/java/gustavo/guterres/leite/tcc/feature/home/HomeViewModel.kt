package gustavo.guterres.leite.tcc.feature.home

import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.repository.HomeRepository
import gustavo.guterres.leite.tcc.feature.base.BaseViewModel
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val repository: HomeRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    val loaderVisibility = ObservableInt(View.GONE)

    val levelList = MutableLiveData<List<Level>>()
    val level = MutableLiveData<Level>()
    val requestInfo = MutableLiveData<String>()

    fun setup() {
        repository
            .fetchLevelsBrief()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loaderVisibility.set(View.VISIBLE) }
            .doFinally { loaderVisibility.set(View.GONE) }
            .subscribe(this::setLevelBrief, this::onError)
            .addTo(compositeDisposable)
    }

    fun fetchLevelDetail(id: String) {
        repository
            .fetchLevelDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loaderVisibility.set(View.VISIBLE) }
            .doFinally { loaderVisibility.set(View.GONE) }
            .subscribe(this::setLevel, this::onError)
            .addTo(compositeDisposable)
    }

    private fun setLevelBrief(response: List<Level>) {
        levelList.value = response
    }

    private fun setLevel(response: Level) {
        level.value = response
    }

    private fun onError(throwable: Throwable) {
        requestInfo.value = resourceProvider.getString(R.string.home_request_error)
    }
}