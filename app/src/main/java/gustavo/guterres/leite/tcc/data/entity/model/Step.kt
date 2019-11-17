package gustavo.guterres.leite.tcc.data.entity.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
data class Step(

    val id: String,

    var points: Double,

    val content: Content,

    var actions: List<Action>,

    var rightActionIdList : List<String>

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readDouble(),
        parcel.readParcelable(Content::class.java.classLoader)!!,
        parcel.createTypedArrayList(Action)!!,
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeDouble(points)
        parcel.writeParcelable(content, flags)
        parcel.writeTypedList(actions)
        parcel.writeStringList(rightActionIdList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Step> {
        override fun createFromParcel(parcel: Parcel): Step {
            return Step(parcel)
        }

        override fun newArray(size: Int): Array<Step?> {
            return arrayOfNulls(size)
        }
    }
}