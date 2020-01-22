package ru.holofox.anicoubs.features.data.network.api.coub.models.timeline

import com.google.gson.annotations.SerializedName

data class SourceFromCoub(
    val id: Int,
    val title: String,
    val url: String,
    val image: String,
    @SerializedName("image_retina")
    val imageRetina: String,
    val duration: Double?,
    @SerializedName("coub_channel_title")
    val coubChannelTitle: String,
    @SerializedName("coub_channel_permalink")
    val coubChannelPermalink: String,
    @SerializedName("coub_views_count")
    val coubViewsCount: Int,
    @SerializedName("coub_permalink")
    val coubPermalink: String
)