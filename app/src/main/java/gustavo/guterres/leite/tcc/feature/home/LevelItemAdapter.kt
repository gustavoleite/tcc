package gustavo.guterres.leite.tcc.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Level
import gustavo.guterres.leite.tcc.databinding.ItemLevelBinding

class LevelItemAdapter() : RecyclerView.Adapter<LevelItemAdapter.ViewHolder>() {

    var list: List<Level> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var levelClick: ((Level) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val viewModel = LevelItemViewModel(list[position], levelClick)
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

    class ViewHolder(val binding: ItemLevelBinding) : RecyclerView.ViewHolder(binding.root)
}