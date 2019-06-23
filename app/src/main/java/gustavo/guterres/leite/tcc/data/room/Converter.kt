package gustavo.guterres.leite.tcc.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gustavo.guterres.leite.tcc.data.entity.output.ActionEntity
import gustavo.guterres.leite.tcc.data.entity.output.ContentEntity
import gustavo.guterres.leite.tcc.data.entity.output.SpotlightEntity
import gustavo.guterres.leite.tcc.data.entity.output.StepEntity

object Converter {

    @TypeConverter
    @JvmStatic
    fun fromSpotlightList(value: List<SpotlightEntity>): String {
        return from(value)
    }

    @TypeConverter
    @JvmStatic
    fun toSpotlightList(value: String): List<SpotlightEntity> {
        return to(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromStepList(value: List<StepEntity>): String {
        return from(value)
    }

    @TypeConverter
    @JvmStatic
    fun toStepList(value: String): List<StepEntity> {
        val type = object : TypeToken<List<StepEntity>>() {}.type

        return Gson()
            .fromJson(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun fromActionList(value: List<ActionEntity>): String {
        return from(value)
    }

    @TypeConverter
    @JvmStatic
    fun toActionList(value: String): List<ActionEntity> {
        return to(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromContent(value: ContentEntity): String {
        return from(value)
    }

    @TypeConverter
    @JvmStatic
    fun toContent(value: String): ContentEntity {
        return to(value)
    }

    private fun <T> from(value: T): String {
        val type = object : TypeToken<T>() {}.type

        return Gson()
            .toJson(value, type)
    }

    private fun <T> to(value: String): T {
        val type = object : TypeToken<T>() {}.type

        return Gson()
            .fromJson(value, type)
    }
}
