package gustavo.guterres.leite.tcc.data.entity.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class Classroom(
    val name: String,
    val students: List<Student>,
    var id: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.createTypedArrayList(Student)!!,
        parcel.readString().orEmpty()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeTypedList(students)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Classroom> {
        override fun createFromParcel(parcel: Parcel): Classroom {
            return Classroom(parcel)
        }

        override fun newArray(size: Int): Array<Classroom?> {
            return arrayOfNulls(size)
        }
    }
}
