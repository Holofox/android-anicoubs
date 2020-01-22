package ru.holofox.anicoubs.features.data.network.api.vk.models.wall

import com.google.gson.annotations.SerializedName

data class VKWallVideo(
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
    val image: List<VKWallImageVersions>,
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