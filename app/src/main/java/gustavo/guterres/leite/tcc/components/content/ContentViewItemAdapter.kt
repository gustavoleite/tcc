package gustavo.guterres.leite.tcc.components.content

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.databinding.ComponentContentItemBinding
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider

class ContentViewItemAdapter(private val resourceProvider: ResourceProvider) :
    RecyclerView.Adapter<ContentViewItemAdapter.ViewHolder>() {

    var list: List<ContentViewItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val viewModel = ContentViewItemViewModel(list[position], resourceProvider)
        binding.viewModel = viewModel
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ComponentContentItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.component_content_item,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ComponentContentItemBinding) : RecyclerView.ViewHolder(binding.root)
}