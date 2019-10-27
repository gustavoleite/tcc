package gustavo.guterres.leite.tcc.feature.home

import android.graphics.drawable.Drawable
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.model.LevelItem
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider

class LevelItemViewModel(
    private val level: LevelItem,
    private var levelClick: ((Level) -> Unit)? = null,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    val number = ObservableField<String>()
    val text = ObservableField<String>()
    val isLevelEnabled = ObservableBoolean(false)
    val firstStar = ObservableField<Drawable>()
    val secondStar = ObservableField<Drawable>()
    val thirdStar = ObservableField<Drawable>()

    init {
        number.set(level.item.number)
        text.set(level.item.name)
        updateItem()
        level.lastLevelUnlocked.addOnPropertyChangedCallback(onLastLevelUnlockedChange())
        level.studentLevel.addOnPropertyChangedCallback(onStudentLevelChange())
    }

    fun onLevelClick() {
        if (isLevelEnabled.get()) {
            levelClick?.invoke(level.item)
        }
    }

    fun updateItem() {
        handleLevelEnable()
        handleStars()
    }

    private fun handleLevelEnable() {
        isLevelEnabled.set(isLevelEnable())
    }

    private fun isLevelEnable() = level.lastLevelUnlocked.get() >= level.itemPosition

    private fun handleStars() {
        level.studentLevel.get()?.hits.let {
            when {
                (it == null || it <= 2) && isLevelEnable() -> {
                    firstStar.set(resourceProvider.getDrawable(R.drawable.ic_star_2))
                    secondStar.set(resourceProvider.getDrawable(R.drawable.ic_star_2))
                    thirdStar.set(resourceProvider.getDrawable(R.drawable.ic_star_2))
                }
                it == 3 -> {
                    firstStar.set(resourceProvider.getDrawable(R.drawable.ic_star))
                    secondStar.set(resourceProvider.getDrawable(R.drawable.ic_star_2))
                    thirdStar.set(resourceProvider.getDrawable(R.drawable.ic_star_2))
                }
                it == 4 -> {
                    firstStar.set(resourceProvider.getDrawable(R.drawable.ic_star))
                    secondStar.set(resourceProvider.getDrawable(R.drawable.ic_star))
                    thirdStar.set(resourceProvider.getDrawable(R.drawable.ic_star_2))
                }
                it != null && it >= 5 -> {
                    firstStar.set(resourceProvider.getDrawable(R.drawable.ic_star))
                    secondStar.set(resourceProvider.getDrawable(R.drawable.ic_star))
                    thirdStar.set(resourceProvider.getDrawable(R.drawable.ic_star))
                }
            }
        }
    }

    private fun onLastLevelUnlockedChange(): Observable.OnPropertyChangedCallback {
        return object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                handleLevelEnable()
            }
        }
    }

    private fun onStudentLevelChange(): Observable.OnPropertyChangedCallback {
        return object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                handleStars()
            }
        }
    }
}