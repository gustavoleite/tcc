package gustavo.guterres.leite.tcc.components.progress

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.components.setProgressValueWithAnimation
import kotlinx.android.synthetic.main.component_progress_view.view.*

class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflateLayout(context)
        loadAtrributes(attrs, context)
    }

    private fun inflateLayout(context: Context) {
        LayoutInflater
            .from(context)
            .inflate(R.layout.component_progress_view, this, true)

        orientation = VERTICAL
    }

    private fun loadAtrributes(attrs: AttributeSet?, context: Context) {
        attrs?.let {
            with(
                context.obtainStyledAttributes(
                    it,
                    R.styleable.ProgressView, 0, 0
                )
            ) {
                loadTextInfoData()
                loadProgressBarData()

                recycle()
            }
        }
    }

    private fun TypedArray.loadProgressBarData() {
        setProgressBarData(
            getDrawable(R.styleable.ProgressView_backgroundDrawable),
            getInt((R.styleable.ProgressView_minValue), 0),
            getInt((R.styleable.ProgressView_maxValue), 100),
            getInt((R.styleable.ProgressView_currentProgress), 0)
        )
    }

    private fun TypedArray.loadTextInfoData() {
        setTextInfoData(
            getString(R.styleable.ProgressView_text),
            getColor(R.styleable.ProgressView_textColor, 0),
            getDimension(R.styleable.ProgressView_textSize, 0f)
        )
    }

    fun setProgressBarData(background: Drawable?, minValue: Int, maxValue: Int, currentValue: Int) {

        pv_progress_bar.apply {
            progressDrawable = background
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                min = minValue
            }
            setMaxValue(maxValue)
            setProgressValueWithAnimation(currentValue)
        }
    }

    fun setTextInfoData(textLabel: String?, @ColorInt textColor: Int, textSize: Float) {
        pv_text_info.apply {
            text = textLabel
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

    fun setMaxValue(value: Int) {
        pv_progress_bar.max = value * 100
    }
}