package ru.holofox.anicoubs.internal.bindings

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter

import com.bumptech.glide.request.RequestOptions

import ru.holofox.anicoubs.R
import ru.holofox.anicoubs.internal.glide.GlideApp

/**
 * Binding adapter used to set dimension ratio for images
 */
@BindingAdapter("imageDimensionRatio")
fun bindImageDimensionRatio(imageView: ImageView, dimensions: String) {
    (imageView.layoutParams as ConstraintLayout.LayoutParams)
        .dimensionRatio = dimensions
}

/**
 * Binding adapter used to display images from URL using Glide
 */
@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        GlideApp.with(imageView.context)
            .load(url)
            .apply(
                RequestOptions()
                    .placeholder(R.color.colorBackground)
                    .error(R.color.colorBackground)
            )
            .into(imageView)
    }
}

/**
 * Binding adapter used to hide the spinner once data is available.
 */
@BindingAdapter("isNetworkError", "items")
fun hideIfNetworkError(view: View, isNetWorkError: Boolean, items: Any?) {
    view.visibility = if (items != null || isNetWorkError) View.GONE else View.VISIBLE
}