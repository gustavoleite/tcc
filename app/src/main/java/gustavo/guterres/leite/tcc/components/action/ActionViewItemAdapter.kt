package gustavo.guterres.leite.tcc.components.action

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import gustavo.guterres.leite.tcc.R
import gustavo.guterres.leite.tcc.data.entity.model.Action
import gustavo.guterres.leite.tcc.databinding.ItemActionBinding
import gustavo.guterres.leite.tcc.databinding.ItemActionImageBinding
import gustavo.guterres.leite.tcc.utils.extensions.resource.ResourceProvider

class ActionViewItemAdapter(private val resourceProvider: ResourceProvider) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var actionClick: ((Action) -> Unit)? = null

    var list: List<Action> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val viewModel = ActionViewItemViewModel(list[position], resourceProvider, actionClick)

        takeIf { holder.itemViewType == IMAGE_BUTTON }
            ?.run { (holder as ImageButtonViewHolder).binding.viewModel = viewModel }
            ?: run { (holder as ButtonViewHolder).binding.viewModel = viewModel }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {

        return list[position].image?.let {
            IMAGE_BUTTON
        } ?: BUTTON
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return takeIf { viewType == IMAGE_BUTTON }
            ?.run {
                ImageButtonViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_action_image,
                        parent,
                        false
                    )
                )
            }
            ?: run {
                ButtonViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_action,
                        parent,
                        false
                    )
                )
            }
    }

    class ButtonViewHolder(val binding: ItemActionBinding) : RecyclerView.ViewHolder(binding.root)
    class ImageButtonViewHolder(val binding: ItemActionImageBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val BUTTON = 1
        private const val IMAGE_BUTTON = 2
    }
}