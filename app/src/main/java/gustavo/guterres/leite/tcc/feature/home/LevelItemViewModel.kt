package gustavo.guterres.leite.tcc.feature.home

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.utils.extensions.toBrCurrency
import java.text.FieldPosition

class LevelItemViewModel(
    private val item: Level,
    private val itemPosition: Int,
    private var levelClick: ((Level) -> Unit)? = null,
    private val studentLevel: ObservableInt
) : ViewModel() {

    val number = ObservableField<String>()
    val text = ObservableField<String>()
    val isLevelEnabled = ObservableBoolean(false)

    init {
        number.set(item.number)
        text.set(item.name)
        isLevelEnabled.set(studentLevel.get() >= itemPosition)
        studentLevel.addOnPropertyChangedCallback(onLevelChange())
    }

    fun onLevelClick() {
        if (isLevelEnabled.get()) {
            levelClick?.invoke(item)
        }
    }

    private fun onLevelChange(): Observable.OnPropertyChangedCallback {
        return object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                isLevelEnabled.set(studentLevel.get() >= itemPosition)
            }
        }
    }
}