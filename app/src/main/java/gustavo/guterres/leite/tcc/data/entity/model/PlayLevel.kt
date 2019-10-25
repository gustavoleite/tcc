package gustavo.guterres.leite.tcc.data.entity.model

import android.os.Parcel
import android.os.Parcelable

data class PlayLevel(
    val level: Level,
    val student: Student,
    val nextLevelId: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Level::class.java.classLoader)!!,
        parcel.readParcelable(Student::class.java.classLoader)!!,
        parcel.readString().orEmpty()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(level, flags)
        parcel.writeParcelable(student, flags)
        parcel.writeString(nextLevelId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayLevel> {
        override fun createFromParcel(parcel: Parcel): PlayLevel {
            return PlayLevel(parcel)
        }

        override fun newArray(size: Int): Array<PlayLevel?> {
            return arrayOfNulls(size)
        }
    }
}