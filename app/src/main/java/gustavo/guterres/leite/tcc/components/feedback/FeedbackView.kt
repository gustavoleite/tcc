package gustavo.guterres.leite.tcc.components.feedback

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.components.setProgressValueWithAnimation
import kotlinx.android.synthetic.main.component_feedback_view.view.*
import kotlinx.android.synthetic.main.component_progress_view.view.*

class FeedbackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var binding : gustavo.guterres.leite.tcc.databinding.ComponentFeedbackViewBinding

    init {
        inflateLayout(context)
        loadAtrributes(attrs, context)
    }

    private fun inflateLayout(context: Context) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.component_feedback_view,
            this,
            true
        )

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
        setStartButtonData(getDrawable(R.styleable.FeedbackView_fvStartButtonDrawable))
    }

    private fun TypedArray.loadEndButtonData() {
        setEndButtonData(
            getDrawable(R.styleable.FeedbackView_fvEndButtonDrawable),
            getString(R.styleable.FeedbackView_fvEndButtonText),
            getColor(R.styleable.FeedbackView_fvEndButtonTextColor, 0),
            getDimension(R.styleable.FeedbackView_fvEndButtonTextSize, 0f)
        )
    }

    private fun TypedArray.loadProgressViewData() {

        binding.fvProgressView.setTextInfoData(
            getString(R.styleable.FeedbackView_fvText),
            getColor(R.styleable.FeedbackView_fvTextColor, 0),
            getDimension(R.styleable.FeedbackView_fvTextSize, 0f)
        )

        binding.fvProgressView.setProgressBarData(
            getDrawable(R.styleable.FeedbackView_fvBackgroundDrawable),
            getInt((R.styleable.FeedbackView_fvMinValue), 0),
            getInt((R.styleable.FeedbackView_fvMaxValue), 100),
            getInt((R.styleable.FeedbackView_fvCurrentProgress), 0)
        )
    }

    fun setStartButtonData(drawable: Drawable?) {
        fv_start_image_view.apply {
            background = drawable
        }
    }

    fun setEndButtonData(drawableStart: Drawable?, textLabel: String?, @ColorInt textColor: Int, textSize: Float) {
        fv_end_button.apply {
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

    companion object {

        @JvmStatic
        @BindingAdapter("fvCurrentProgress")
        fun setFvCurrentProgress(view: FeedbackView, value: ObservableInt) {
            view.binding.fvProgressView.pv_progress_bar.setProgressValueWithAnimation(value.get())
        }

        @JvmStatic
        @BindingAdapter("fvMaxValue")
        fun setFvMaxValue(view: FeedbackView, value: ObservableInt) {
            view.binding.fvProgressView.setMaxValue(value.get())
        }

        @JvmStatic
        @BindingAdapter("fvText")
        fun setFvText(view: FeedbackView, value: ObservableField<String>) {
            view.binding.fvProgressView.pv_text_info.text = value.get()
        }

        @JvmStatic
        @BindingAdapter("fvStartButtonOnClick")
        fun setFvStartButtonOnClick(view: FeedbackView, onClickListener: OnClickListener) {
            view.binding.fvStartImageView.setOnClickListener(onClickListener)
        }
    }
}