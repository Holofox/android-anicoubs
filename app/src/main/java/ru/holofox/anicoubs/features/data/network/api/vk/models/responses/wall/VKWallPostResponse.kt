package ru.holofox.anicoubs.features.data.network.api.vk.models.responses.wall

import com.google.gson.annotations.SerializedName

data class VKWallPostResponse(
    @SerializedName("post_id")
    val postId: Int
)