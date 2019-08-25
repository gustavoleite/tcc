package gustavo.guterres.leite.tcc.data.entity.model

import android.os.Parcel
import android.os.Parcelable

data class Content(

    val description: String,

    val spotlights: List<Spotlight>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.createTypedArrayList(Spotlight)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeTypedList(spotlights)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Content> {
        override fun createFromParcel(parcel: Parcel): Content {
            return Content(parcel)
        }

        override fun newArray(size: Int): Array<Content?> {
            return arrayOfNulls(size)
        }
    }
}