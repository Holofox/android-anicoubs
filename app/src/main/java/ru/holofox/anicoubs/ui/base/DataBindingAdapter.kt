package ru.holofox.anicoubs.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class DataBindingAdapter<T>(
    private val variableId: Int,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBindingAdapter.DataBindingViewHolder<T>>(diffCallback) {

    private var list: List<T>? = emptyList()

    private lateinit var onItemViewClick: ((View, T, Int) -> Unit?)
    private lateinit var clickableIds: IntArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)

        return DataBindingViewHolder(binding, variableId)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        holder.apply {
            bind(getItem(position))

            if (::onItemViewClick.isInitialized && ::clickableIds.isInitialized) {
                val clickListener = View.OnClickListener { view ->
                    list?.get(holder.adapterPosition)?.let {
                        onItemViewClick.invoke(view, it, holder.adapterPosition)
                    }
                }
                for (clickableId in clickableIds) {
                    holder.itemView.findViewById<View>(clickableId)
                        ?.setOnClickListener(clickListener)
                }
            }
        }
    }

    override fun submitList(list: List<T>?) {
        super.submitList(list)
        this.list = list
    }

    fun setOnItemViewClickListener(
        onItemViewClick: (view: View, model: T, position: Int) -> Unit,
        vararg ids: Int
    ) {
        this.onItemViewClick = onItemViewClick
        this.clickableIds = ids
    }

    class DataBindingViewHolder<T>(
        private val binding: ViewDataBinding,
        private val variableId: Int
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            binding.setVariable(variableId, item)
            binding.executePendingBindings()
        }
    }

}