package gustavo.guterres.leite.tcc.components.action

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Action
import kotlinx.android.synthetic.main.component_action_view.view.*
import org.koin.standalone.KoinComponent

class ActionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), KoinComponent {

    private var actionClick: ((Action) -> Unit)? = null

    private var buttonDrawableRes: Int = 0
    private var buttonTextColor: Int = 0
    private var buttonTextSize: Float = 0f
    private var buttonWidth: Int = 0
    private var buttonHeight: Int = 0
    private var buttonMarginHorizontal: Int = 0
    private var buttonMarginVertical: Int = 0

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
            getDimensionPixelSize(R.styleable.ActionView_avMarginVertical, 0),
            getResourceId(R.styleable.ActionView_avBackgroundDrawable, -1),
            getColor(R.styleable.ActionView_avTextColor, 0),
            getDimension(R.styleable.ActionView_avTextSize, 0f)
        )
    }

    fun setButtonData(
        @Dimension width: Int,
        @Dimension height: Int,
        @Dimension marginHorizontal: Int,
        @Dimension marginVertical: Int,
        @DrawableRes drawableRes: Int,
        @ColorInt textColor: Int,
        @Dimension textSize: Float
    ) {
        this.buttonWidth = width
        this.buttonHeight = height
        this.buttonMarginHorizontal = marginHorizontal
        this.buttonMarginVertical = marginVertical
        this.buttonTextColor = textColor
        this.buttonTextSize = textSize
        this.buttonDrawableRes = drawableRes
    }


    private fun buildView(list: List<Action>) {

        list.forEachIndexed { index, action ->
            if (index < FIRST_LINE_VIEW_LIMIT) {
                av_first_line?.addView(buildButton(action))
            } else if (index < SECOUND_LINE_VIEW_LIMIT) {
                av_second_line?.addView(buildButton(action))
            }
        }
    }

    private fun buildButton(action: Action): Button {

        return Button(context).apply {
            layoutParams = LayoutParams(buttonWidth, buttonHeight).apply {
                takeIf { buttonWidth == LayoutParams.WRAP_CONTENT }
                    ?.run { weight = 1f }
                setMargins(
                    buttonMarginHorizontal,
                    buttonMarginVertical,
                    buttonMarginHorizontal,
                    buttonMarginVertical
                )
            }
            takeIf { this@ActionView.buttonDrawableRes != 0 }
                ?.run {
                    setBackgroundResource(buttonDrawableRes)
                }
            takeIf { this@ActionView.buttonTextColor != 0 }
                ?.run {
                    setTextColor(buttonTextColor)
                }
            takeIf { this@ActionView.buttonTextSize != 0f }
                ?.run {
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                }
            text = action.text
            setOnClickListener { actionClick?.invoke(action) }
        }
    }

    fun setAvOnClick(onClick: ((Action) -> Unit)?) {
        actionClick = onClick
    }

    companion object {
        private const val FIRST_LINE_VIEW_LIMIT = 3
        private const val SECOUND_LINE_VIEW_LIMIT = 6
    }
}