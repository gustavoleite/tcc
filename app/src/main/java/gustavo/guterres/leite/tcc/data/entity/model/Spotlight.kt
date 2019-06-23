package gustavo.guterres.leite.tcc.data.entity.model

import android.os.Parcel
import android.os.Parcelable

data class Spotlight(

    val image: Int,

    val value: Double? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeValue(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Spotlight> {
        override fun createFromParcel(parcel: Parcel): Spotlight {
            return Spotlight(parcel)
        }

        override fun newArray(size: Int): Array<Spotlight?> {
            return arrayOfNulls(size)
        }
    }
}