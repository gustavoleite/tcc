package gustavo.guterres.leite.tcc.utils.extensions.resource

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes resourceId: Int, vararg formatArgs: Any?): String
    fun getDrawable(@DrawableRes resourceId: Int): Drawable?
}