package gustavo.guterres.leite.tcc.data.entity.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.Exclude

data class StudentLevel(
    var id: String,
    var hits: Int = 0,
    var mistakes: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(hits)
        parcel.writeInt(mistakes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentLevel> {
        override fun createFromParcel(parcel: Parcel): StudentLevel {
            return StudentLevel(parcel)
        }

        override fun newArray(size: Int): Array<StudentLevel?> {
            return arrayOfNulls(size)
        }
    }
}