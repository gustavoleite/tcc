package gustavo.guterres.leite.tcc.components.progress

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import gustavo.guterres.leite.tcc.R
import kotlinx.android.synthetic.main.component_progress.view.*

class ProgressComponent @JvmOverloads constructor(
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
            .inflate(R.layout.component_progress, this, true)

        orientation = VERTICAL
    }

    private fun loadAtrributes(attrs: AttributeSet?, context: Context) {
        attrs?.let {
            with(
                context.obtainStyledAttributes(
                    it,
                    R.styleable.ProgressComponent, 0, 0
                )
            ) {
                loadLabelData()
                loadProgressBarData()

                recycle()
            }
        }
    }

    private fun TypedArray.loadProgressBarData() {
        cp_progress_bar.apply {
            progressDrawable = getDrawable(R.styleable.ProgressComponent_backgroundDrawable)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                min = getInt((R.styleable.ProgressComponent_minValue), 0)
            }
            setMaxValue(getInt((R.styleable.ProgressComponent_maxValue), 100))
            setProgressWithAnimation(getInt((R.styleable.ProgressComponent_currentProgress), 0))
        }
    }

    private fun TypedArray.loadLabelData() {
        cp_start_label.text = getString(R.styleable.ProgressComponent_text)

        cp_start_label.apply {
            text = getString(R.styleable.ProgressComponent_text)

            getColor(R.styleable.ProgressComponent_textColor, 0).let { newTextColor ->
                takeIf { newTextColor != 0 }
                    ?.run {
                        this@apply.setTextColor(newTextColor)
                    }
            }

            getDimension(R.styleable.ProgressComponent_textSize, 0f).let { newTextSize ->
                takeIf { newTextSize != 0f }
                    ?.run {
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize)
                    }
            }
        }
    }

    fun setMaxValue(value: Int) {
        cp_progress_bar.max = value * 100
    }

    fun setProgressWithAnimation(progressTo: Int, animationDuration: Long = 1000) {
        ObjectAnimator.ofInt(
            cp_progress_bar,
            "progress",
            cp_progress_bar.progress,
            progressTo * 100
        )
            .apply {
                duration = animationDuration
                interpolator = DecelerateInterpolator()
                start()
            }
    }
}