package gustavo.guterres.leite.tcc.data.entity.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.Exclude

data class Student(
    val id: String = "",
    var name: String = "",
    val studentLevel: MutableList<StudentLevel> = arrayListOf(),
    var currentLevel: Int = 0,
    var accumulatedPoints: Double = 0.0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.createTypedArrayList(StudentLevel) ?: arrayListOf(),
        parcel.readInt(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeTypedList(studentLevel)
        parcel.writeInt(currentLevel)
        parcel.writeDouble(accumulatedPoints)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
    }
}