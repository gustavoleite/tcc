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
                AvaliableImages.LAPIS -> R.drawable.ic_pencil
                AvaliableImages.BORRACHA -> R.drawable.ic_rubber
                AvaliableImages.COLA -> R.drawable.ic_glue
                AvaliableImages.FOGAO -> R.drawable.ic_stove
                AvaliableImages.PANELA -> R.drawable.ic_pan
                AvaliableImages.GELADEIRA -> R.drawable.ic_fridge
                AvaliableImages.PAO -> R.drawable.ic_bread
                AvaliableImages.BALA -> R.drawable.ic_sweets
                AvaliableImages.SUCO -> R.drawable.ic_juice
                AvaliableImages.ERVILHA -> R.drawable.ic_peas
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
    CADERNO,
    LAPIS,
    BORRACHA,
    COLA,
    FOGAO,
    PANELA,
    GELADEIRA,
    PAO,
    BALA,
    SUCO,
    ERVILHA
}
