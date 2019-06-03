package gustavo.guterres.leite.tcc.components.content

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.databinding.ComponentContentViewBinding
import kotlinx.android.synthetic.main.component_content_view.view.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ContentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), KoinComponent {

    private val listAdapter: ContentViewItemAdapter by inject()

    init {
        inflateLayout(context)
        loadAtrributes(attrs, context)
        setupList(context)
    }

    private fun inflateLayout(context: Context) {
        DataBindingUtil.inflate<ComponentContentViewBinding>(
            LayoutInflater.from(context),
            R.layout.component_content_view, this, true
        )

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

    fun setTextData(textLabel: String?, @ColorInt textColor: Int, @Dimension textSize: Float) {
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

    fun setList(items: List<ContentViewItem>) {
        listAdapter.list = items
    }

    private fun setupList(context: Context) {
        cv_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = listAdapter
        }
    }
}