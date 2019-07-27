package ru.holofox.anicoubs.data.network.response

import com.google.gson.annotations.SerializedName

data class VKWallPostResponse(
    @SerializedName("post_id")
    val postId: Int
)