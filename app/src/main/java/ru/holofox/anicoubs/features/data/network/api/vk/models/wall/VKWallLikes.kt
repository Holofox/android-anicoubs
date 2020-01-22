package ru.holofox.anicoubs.features.data.network.api.vk.models.wall

import com.google.gson.annotations.SerializedName

data class VKWallLikes(
    @SerializedName("can_like")
    val canLike: Int,
    @SerializedName("can_publish")
    val canPublish: Int,
    val count: Int,
    @SerializedName("user_likes")
    val userLikes: Int
)