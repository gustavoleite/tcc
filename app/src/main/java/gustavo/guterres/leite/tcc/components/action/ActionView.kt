package gustavo.guterres.leite.tcc.components.action

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.databinding.BindingAdapter
import gustavo.guterres.leite.tcc.R
import kotlinx.android.synthetic.main.component_action_view.view.*
import org.koin.standalone.KoinComponent

class ActionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), KoinComponent {

    private lateinit var buttonLayout: Button
    private var actionClick: ((Action) -> Unit)? = null

    var list: List<Action> = emptyList()
        set(value) {
            field = value
            buildView(field)
        }

    init {
        inflateLayout(context)
        loadAtrributes(attrs, context)
    }

    private fun inflateLayout(context: Context) {
        LayoutInflater
            .from(context)
            .inflate(R.layout.component_action_view, this, true)

        orientation = VERTICAL
    }

    private fun loadAtrributes(attrs: AttributeSet?, context: Context) {
        attrs?.let {
            with(
                context.obtainStyledAttributes(
                    it,
                    R.styleable.ActionView, 0, 0
                )
            ) {
                loadButtonData()

                recycle()
            }
        }
    }

    private fun TypedArray.loadButtonData() {
        setButtonData(
            getLayoutDimension(R.styleable.ActionView_avWidth, ViewGroup.LayoutParams.WRAP_CONTENT),
            getLayoutDimension(R.styleable.ActionView_avHeight, ViewGroup.LayoutParams.WRAP_CONTENT),
            getDimensionPixelSize(R.styleable.ActionView_avMarginHorizontal, 0),
            getDrawable(R.styleable.ActionView_avBackgroundDrawable),
            getColor(R.styleable.ActionView_avTextColor, 0),
            getDimension(R.styleable.ActionView_avTextSize, 0f)
        )
    }

    fun setButtonData(
        @Dimension width: Int,
        @Dimension height: Int,
        @Dimension marginHorizontal: Int,
        drawable: Drawable?,
        @ColorInt textColor: Int,
        @Dimension textSize: Float
    ) {

        buttonLayout = Button(context).apply {
            layoutParams = LayoutParams(width, height).apply { setMargins(marginHorizontal, 0, marginHorizontal, 0) }
            background = drawable
            takeIf { textColor != 0 }
                ?.run {
                    setTextColor(textColor)
                }
            takeIf { textSize != 0f }
                ?.run {
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                }
        }
    }

    private fun buildView(list: List<Action>) {
        for (action in list) {
            av_container?.addView(Button(context).apply {
                layoutParams = buttonLayout.layoutParams
                buttonLayout.background?.let {
                    background = it
                }
                setTextColor(buttonLayout.currentTextColor)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonLayout.textSize)
                text = action.text
                //setTag(BUTTON_TAG, action.id)
                actionClick?.invoke(action)
            })
        }
    }

    fun setAvOnClick(onClick: ((Action) -> Unit)?) {
        actionClick = onClick
    }

    companion object {
        private const val BUTTON_TAG = 1
    }
}