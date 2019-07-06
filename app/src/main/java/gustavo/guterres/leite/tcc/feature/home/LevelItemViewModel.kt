package gustavo.guterres.leite.tcc.feature.home

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import gustavo.guterres.leite.tcc.data.entity.model.Level

class LevelItemViewModel(
    private val item: Level,
    private var levelClick: ((Level) -> Unit)? = null
) : ViewModel() {

    val number = ObservableField<String>()
    val text = ObservableField<String>()

    init {
        number.set(item.number)
        text.set(item.name)
    }

    fun onLevelClick() {
        levelClick?.invoke(item)
    }
}