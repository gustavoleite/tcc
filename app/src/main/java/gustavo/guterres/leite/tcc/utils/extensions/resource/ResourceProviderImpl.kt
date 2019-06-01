package gustavo.guterres.leite.tcc.utils.extensions.resource

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class ResourceProviderImpl constructor(private val context: Context) : ResourceProvider {

    override fun getString(resourceId: Int, vararg formatArgs: Any?): String {
        return context.getString(resourceId, formatArgs)
    }

    override fun getDrawable(resourceId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resourceId)
    }
}