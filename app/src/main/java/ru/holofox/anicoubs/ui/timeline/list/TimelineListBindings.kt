package ru.holofox.anicoubs.ui.timeline.list

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.Tag
import ru.holofox.anicoubs.features.data.network.api.coub.models.timeline.Versions
import ru.holofox.anicoubs.internal.bindings.bindImageUrl

object TimelineListBindings {

    /**
     * Binding adapter used to display images from Avatar[Versions] using Glide
     */
    @BindingAdapter("imageUrlFromAvatarVersions")
    @JvmStatic
    fun bindImageFromAvatarVersions(imageView: ImageView, versions: Versions) {
        bindImageUrl(
            imageView,
            versions.getImageUrl("profile_pic_new")
        )
    }

    /**
     * Binding adapter used to display images from FirstFrame[Versions] using Glide
     */
    @BindingAdapter("imageUrlFromFirstFrameVersions")
    @JvmStatic
    fun bindImageFromFirstFrameVersions(imageView: ImageView, versions: Versions) {
        bindImageUrl(imageView, versions.getImageUrl("med"))
    }

    /**
     * Binding adapter used to display tags in [ChipGroup] from [Tag]
     */
    @BindingAdapter("tags")
    @JvmStatic
    fun bindTags(chipGroup: ChipGroup, tags: List<Tag>?) {
        tags?.let {
            for (index in it.indices) {
                val chip = Chip(chipGroup.context)
                chip.text = tags[index].title
                chip.isClickable = true
                chipGroup.addView(chip)
            }
        }
    }
}