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
) :
    ListAdapter<T, DataBindingAdapter.DataBindingViewHolder<T>>(diffCallback) {

    lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)

        return DataBindingViewHolder(binding, variableId)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        holder.apply {
            bind(getItem(position))

            if (::listener.isInitialized) {
                itemView.setOnClickListener {
                    listener.onItemClick(it)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class DataBindingViewHolder<T>(
        private val binding: ViewDataBinding,
        private val variableId: Int
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            binding.setVariable(variableId, item)
            binding.executePendingBindings()
        }
    }

}