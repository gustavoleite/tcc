package gustavo.guterres.leite.tcc.data.entity.model

import android.os.Parcel
import android.os.Parcelable

data class Onboarding(
    val title: String,
    val message: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Onboarding> {
        override fun createFromParcel(parcel: Parcel): Onboarding {
            return Onboarding(parcel)
        }

        override fun newArray(size: Int): Array<Onboarding?> {
            return arrayOfNulls(size)
        }
    }
}