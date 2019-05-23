package gustavo.guterres.leite.tcc.components.progress

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import gustavo.guterres.leite.tcc.R
import kotlinx.android.synthetic.main.component_feedback_view.view.*
import kotlinx.android.synthetic.main.component_progress_view.view.*

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
                    R.styleable.ProgressView, 0, 0
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
        cf_start_image_view.apply {
            background = getDrawable(R.styleable.FeedbackView_startButtonDrawable)
        }
        //@TODO setar onclick
    }

    private fun TypedArray.loadEndButtonData() {
        cf_end_button.apply {
            background = getDrawable(R.styleable.FeedbackView_endButtonDrawable)

            text = getString(R.styleable.FeedbackView_endButtonText)

            getColor(R.styleable.FeedbackView_endButtonTextColor, 0).let { newTextColor ->
                takeIf { newTextColor != 0 }
                    ?.run {
                        this@apply.setTextColor(newTextColor)
                    }
            }

            getDimension(R.styleable.FeedbackView_endButtonTextSize, 0f).let { newTextSize ->
                takeIf { newTextSize != 0f }
                    ?.run {
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize)
                    }
            }
        }
        //@TODO setar onclick
    }

    private fun TypedArray.loadProgressViewData() {
        cf_progress_view.cp_start_label.text = getString(R.styleable.FeedbackView_pvText)
        //@TODO verificar se nao é interessante adicionar uma viewmodel para fazer o binding desses valores
    }
}