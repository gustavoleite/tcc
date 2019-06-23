package gustavo.guterres.leite.tcc.data.entity.mapper

import android.util.Log
import gustavo.guterres.leite.tcc.R

object ImageMapper {

    fun toDrawableId(image: String?): Int? {
        return when (image) {
            "CEM_REAIS" -> R.drawable.ic_hundred_reais
            else -> {
                Log.w("ImageMapper", "Failed to find image $image")
                null
            }
        }
    }
}