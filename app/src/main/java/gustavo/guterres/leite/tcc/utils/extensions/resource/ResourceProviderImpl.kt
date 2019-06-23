package gustavo.guterres.leite.tcc.utils.extensions.resource

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class ResourceProviderImpl constructor(private val context: Context) : ResourceProvider {

    override fun getString(@StringRes resourceId: Int, vararg formatArgs: Any?): String {
        return context.getString(resourceId, *formatArgs)
    }

    override fun getDrawable(@DrawableRes resourceId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resourceId)
    }
}