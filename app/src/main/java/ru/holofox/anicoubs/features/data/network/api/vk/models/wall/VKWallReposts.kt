package ru.holofox.anicoubs.features.data.network.api.vk.models.wall

import com.google.gson.annotations.SerializedName

data class VKWallReposts(
    val count: Int,
    @SerializedName("user_reposted")
    val userReposted: Int
)