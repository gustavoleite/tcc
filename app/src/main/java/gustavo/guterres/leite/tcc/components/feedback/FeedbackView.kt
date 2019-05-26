package gustavo.guterres.leite.tcc.components.feedback

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import gustavo.guterres.leite.tcc.R
import kotlinx.android.synthetic.main.component_feedback_view.view.*

class FeedbackView @JvmOverloads constructor(
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
            .inflate(R.layout.component_feedback_view, this, true)

        orientation = VERTICAL
    }

    private fun loadAtrributes(attrs: AttributeSet?, context: Context) {
        attrs?.let {
            with(
                context.obtainStyledAttributes(
                    it,
                    R.styleable.FeedbackView, 0, 0
                )
            ) {
                loadStartButtonData()
                loadEndButtonData()
                loadProgressViewData()

                recycle()
            }
        }
    }

    private fun TypedArray.loadStartButtonData() {
        setStartButtonData(getDrawable(R.styleable.FeedbackView_startButtonDrawable))
        //@TODO setar onclick
    }

    private fun TypedArray.loadEndButtonData() {
        setEndButtonData(
            getDrawable(R.styleable.FeedbackView_endButtonDrawable),
            getString(R.styleable.FeedbackView_endButtonText),
            getColor(R.styleable.FeedbackView_endButtonTextColor, 0),
            getDimension(R.styleable.FeedbackView_endButtonTextSize, 0f)
        )
        //@TODO setar onclick
    }

    private fun TypedArray.loadProgressViewData() {

        cf_progress_view.setLabelData(
            getString(R.styleable.FeedbackView_pvText),
            getColor(R.styleable.FeedbackView_pvTextColor, 0),
            getDimension(R.styleable.FeedbackView_pvTextSize, 0f)
        )

        cf_progress_view.setProgressBarData(
            getDrawable(R.styleable.FeedbackView_pvBackgroundDrawable),
            getInt((R.styleable.FeedbackView_pvMinValue), 0),
            getInt((R.styleable.FeedbackView_pvMaxValue), 100),
            getInt((R.styleable.FeedbackView_pvCurrentProgress), 0)
        )
    }

    fun setStartButtonData(drawable: Drawable?) {
        cf_start_image_view.apply {
            background = drawable
        }
    }

    fun setEndButtonData(drawableStart: Drawable?, textLabel: String?, @ColorInt textColor: Int, textSize: Float) {
        cf_end_button.apply {
            setCompoundDrawablesWithIntrinsicBounds(drawableStart, null, null, null)
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
}