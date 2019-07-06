package gustavo.guterres.leite.tcc.components.action

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import gustavo.guterres.leite.tcc.data.entity.model.Action
import gustavo.guterres.leite.tcc.data.entity.model.Spotlight
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import gustavo.guterres.leite.tcc.utils.extensions.toBrCurrency

class ActionViewItemViewModel(
    private val item: Action,
    resourceProvider: ResourceProvider,
    private var actionClick: ((Action) -> Unit)? = null
) : ViewModel() {

    val image = ObservableField<Drawable>()
    val label = ObservableField<String>()

    init {
        with(item) {
            image?.let {
                this@ActionViewItemViewModel.image.set(resourceProvider.getDrawable(it))
            } ?: run {
                label.set(text)
            }
        }
    }

    fun onActionClick() {
        actionClick?.invoke(item)
    }
}