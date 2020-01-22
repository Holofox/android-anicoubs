package ru.holofox.anicoubs.ui.timeline.list

import androidx.recyclerview.widget.DiffUtil
import ru.holofox.anicoubs.BR
import ru.holofox.anicoubs.R
import ru.holofox.anicoubs.features.domain.db.unitlocalized.coub.UnitSpecificTimelineMinimalEntry
import ru.holofox.anicoubs.ui.base.DataBindingAdapter

/**
 * This class implements a RecyclerView ListAdapter which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class TimelineAdapter :
    DataBindingAdapter<UnitSpecificTimelineMinimalEntry>(BR.timelineListModel, DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<UnitSpecificTimelineMinimalEntry>() {
        override fun areItemsTheSame(
            oldItem: UnitSpecificTimelineMinimalEntry,
            newItem: UnitSpecificTimelineMinimalEntry
        ): Boolean = oldItem.permalink == newItem.permalink

        override fun areContentsTheSame(
            oldItem: UnitSpecificTimelineMinimalEntry,
            newItem: UnitSpecificTimelineMinimalEntry
        ): Boolean = oldItem == newItem
    }

    override fun getItemViewType(position: Int) = R.layout.item_timeline
}