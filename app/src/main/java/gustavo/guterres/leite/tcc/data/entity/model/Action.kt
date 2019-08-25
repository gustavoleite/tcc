package gustavo.guterres.leite.tcc.data.entity.model

import android.os.Parcel
import android.os.Parcelable

data class Action(

    val id: String,

    val text: String? = null,

    val image: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(text)
        parcel.writeValue(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Action> {
        override fun createFromParcel(parcel: Parcel): Action {
            return Action(parcel)
        }

        override fun newArray(size: Int): Array<Action?> {
            return arrayOfNulls(size)
        }
    }
}