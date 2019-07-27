package ru.holofox.anicoubs.data.db.entity.coub.timeline

import com.google.gson.annotations.SerializedName

data class Category(
    val id: Int,
    val title: String,
    val permalink: String,
    @SerializedName("subscriptions_count")
    val subscriptionsCount: Int,
    /*
    @SerializedName("big_image_url")
    val bigImageUrl: String,
    @SerializedName("small_image_url")
    val smallImageUrl: String,
    @SerializedName("med_image_url")
    val medImageUrl: String, */
    val visible: Boolean
)