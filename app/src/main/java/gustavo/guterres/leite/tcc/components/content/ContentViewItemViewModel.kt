package gustavo.guterres.leite.tcc.components.content

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import gustavo.guterres.leite.tcc.data.entity.model.Spotlight
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider
import gustavo.guterres.leite.tcc.utils.extensions.toPoints

class ContentViewItemViewModel(item: Spotlight, resourceProvider: ResourceProvider) : ViewModel() {

    val image = ObservableField<Drawable>()
    val label = ObservableField<String>()
    val iconVisilibity = ObservableInt(View.GONE)

    init {
        image.set(resourceProvider.getDrawable(item.image))
        item.value?.let {
            label.set(it.toPoints())
            iconVisilibity.set(View.VISIBLE)
        }
    }
}