package gustavo.guterres.leite.tcc.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.data.entity.model.LevelItem
import gustavo.guterres.leite.tcc.databinding.ItemLevelBinding
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider

class LevelItemAdapter(private val resourceProvider: ResourceProvider) : RecyclerView.Adapter<LevelItemAdapter.ViewHolder>() {

    var list: List<LevelItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var levelClick: ((Level, Boolean) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val viewModel = LevelItemViewModel(list[position].apply { itemPosition = position }, levelClick, resourceProvider)
        binding.viewModel = viewModel
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemLevelBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_level,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    fun setItemCallback(itemClick: ((Level, Boolean) -> Unit)?) {
        this.levelClick = itemClick
    }

    class ViewHolder(val binding: ItemLevelBinding) : RecyclerView.ViewHolder(binding.root)
}