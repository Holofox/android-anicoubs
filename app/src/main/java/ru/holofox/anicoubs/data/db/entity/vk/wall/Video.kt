package ru.holofox.anicoubs.data.db.entity.vk.wall

import com.google.gson.annotations.SerializedName

data class Video(
    val id: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    val title: String,
    val duration: Int,
    val description: String?,
    val date: Int,
    val comments: Int,
    val views: Int,
    @SerializedName("local_views")
    val localViews: Int,
    val image: List<ImageVersions>,
    @SerializedName("is_favorite")
    val isFavorite: Boolean,
    @SerializedName("access_key")
    val accessKey: String,
    val platform: String,
    @SerializedName("can_edit")
    val canEdit: Int,
    @SerializedName("can_add")
    val canAdd: Int,
    @SerializedName("track_code")
    val trackCode: String,
    val type: String
)