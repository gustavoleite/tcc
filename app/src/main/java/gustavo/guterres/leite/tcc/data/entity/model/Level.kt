package gustavo.guterres.leite.tcc.data.entity.model

import android.os.Parcel
import android.os.Parcelable

data class Level(

    val id: String,

    val name: String,

    val number: String? = null,

    val steps: List<Step>? = null,

    val onboardings: List<Onboarding>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString(),
        parcel.createTypedArrayList(Step),
        parcel.createTypedArrayList(Onboarding)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(number)
        parcel.writeTypedList(steps)
        parcel.writeTypedList(onboardings)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Level> {
        override fun createFromParcel(parcel: Parcel): Level {
            return Level(parcel)
        }

        override fun newArray(size: Int): Array<Level?> {
            return arrayOfNulls(size)
        }
    }
}