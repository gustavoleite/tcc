package gustavo.guterres.leite.tcc.components.content

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import gustavo.guterres.leite.tcc.R
import kotlinx.android.synthetic.main.component_content_view.view.*
import kotlinx.android.synthetic.main.component_feedback_view.view.*

class ContentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var listAdapter: ContentViewItemAdapter

    init {
        inflateLayout(context)
        loadAtrributes(attrs, context)
        setupList(context)
    }

    private fun inflateLayout(context: Context) {
        LayoutInflater
            .from(context)
            .inflate(R.layout.component_content_view, this, true)

        orientation = VERTICAL
    }

    private fun loadAtrributes(attrs: AttributeSet?, context: Context) {
        attrs?.let {
            with(
                context.obtainStyledAttributes(
                    it,
                    R.styleable.ContentView, 0, 0
                )
            ) {
                loadTextData()

                recycle()
            }
        }
    }

    private fun TypedArray.loadTextData() {
        setTextData(
            getString(R.styleable.ContentView_cvText),
            getColor(R.styleable.ContentView_cvTextColor, 0),
            getDimension(R.styleable.ContentView_cvTextSize, 0f)
        )
    }

    fun setTextData(textLabel: String?, @ColorInt textColor: Int, textSize: Float) {
        cv_title.apply {
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

    private fun setupList(context: Context) {
        listAdapter = ContentViewItemAdapter(context)

        cv_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = listAdapter
        }
    }

    fun setList(items: List<ContentViewItem>) {
        listAdapter.list = items
    }

    @BindingAdapter("app:cvItems")
    fun setCvItems(contentView: ContentView, items: List<ContentViewItem>) {
        contentView.setList(items)
    }
}