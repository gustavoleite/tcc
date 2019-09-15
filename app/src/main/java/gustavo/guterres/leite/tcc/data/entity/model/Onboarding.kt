package gustavo.guterres.leite.tcc.data.entity.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Onboarding(
    val id: String,
    val title: String,
    val message: String
) : Parcelable