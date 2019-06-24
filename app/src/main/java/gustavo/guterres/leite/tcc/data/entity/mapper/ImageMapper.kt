package gustavo.guterres.leite.tcc.data.entity.mapper

import android.util.Log
import gustavo.guterres.leite.tcc.R

object ImageMapper {

    fun toDrawableId(image: String?): Int? {

        image?.let {
            return when (AvaliableImages.valueOf(image)) {
                AvaliableImages.CEM_REAIS -> R.drawable.ic_note_hundred
                AvaliableImages.CINQUENTA_REAIS -> R.drawable.ic_note_fifty
                AvaliableImages.VINTE_REAIS -> R.drawable.ic_note_twenty
                AvaliableImages.DEZ_REAIS -> R.drawable.ic_note_ten
                AvaliableImages.CINCO_REAIS -> R.drawable.ic_note_five
                AvaliableImages.DOIS_REAIS -> R.drawable.ic_note_two
                AvaliableImages.URSO -> R.drawable.ic_bear
                AvaliableImages.CADERNO -> R.drawable.ic_notebook
            }
        } ?: run {
            Log.w("ImageMapper", "Failed to find image $image")
            return null
        }
    }
}

enum class AvaliableImages {
    CEM_REAIS,
    CINQUENTA_REAIS,
    VINTE_REAIS,
    DEZ_REAIS,
    CINCO_REAIS,
    DOIS_REAIS,
    URSO,
    CADERNO
}