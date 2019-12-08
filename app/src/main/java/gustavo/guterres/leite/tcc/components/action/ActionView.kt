package gustavo.guterres.leite.tcc.components.action

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Action
import gustavo.guterres.leite.tcc.databinding.ComponentActionViewBinding
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ActionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), KoinComponent {

    private val listAdapter: ActionViewItemAdapter by inject()

    var list: List<Action> = emptyList()
        set(value) {
            field = value
            listAdapter.list = field
            setupList(this.context)
        }

    private lateinit var binding: ComponentActionViewBinding

    init {
        inflateLayout(context)
        setupList(context)
    }

    private fun inflateLayout(context: Context) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.component_action_view,
            this,
            true
        )

        orientation = VERTICAL
    }

    fun setAvOnClick(onClick: ((Action) -> Unit)?) {
        listAdapter.actionClick = onClick
    }

    private fun setupList(context: Context) {
        binding.avRecyclerView.apply {
            layoutManager = GridLayoutManager(context, getColumns())
            adapter = listAdapter
        }
    }

    private fun getColumns(): Int {
        return if (list.size in 1..4) {
            list.size
        } else {
            3
        }
    }
}
