package gustavo.guterres.leite.tcc.utils.extensions

import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String) {
        Toast.makeText(view.context, imageUrl, Toast.LENGTH_SHORT).show()
    }
}