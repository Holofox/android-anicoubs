package ru.holofox.anicoubs.ui.postponed.list

import androidx.recyclerview.widget.DiffUtil
import ru.holofox.anicoubs.BR
import ru.holofox.anicoubs.R

import ru.holofox.anicoubs.data.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry
import ru.holofox.anicoubs.ui.base.DataBindingAdapter

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class PostponedListAdapter :
    DataBindingAdapter<UnitSpecificVKWallMinimalEntry>(BR.postponedListModel, DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<UnitSpecificVKWallMinimalEntry>() {
        override fun areItemsTheSame(
            oldItem: UnitSpecificVKWallMinimalEntry,
            newItem: UnitSpecificVKWallMinimalEntry
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: UnitSpecificVKWallMinimalEntry,
            newItem: UnitSpecificVKWallMinimalEntry
        ): Boolean = oldItem == newItem
    }

    override fun getItemViewType(position: Int) = R.layout.item_postponed
}