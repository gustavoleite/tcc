package gustavo.guterres.leite.tcc.feature.home

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

    val levelList = MutableLiveData<List<Level>>()
    val requestInfo = MutableLiveData<String>()

    fun setup() {
        repository
            .fetchLevelsBrief()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::setData, this::onError)
            .addTo(compositeDisposable)
    }

    fun fetchLevelDetail(id: String) {

    }

    private fun setData(response: List<Level>) {
        levelList.value = response
    }

    private fun onError(throwable: Throwable) {
        requestInfo.value = resourceProvider.getString(R.string.home_request_error)
    }
}