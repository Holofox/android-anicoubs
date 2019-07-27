package ru.holofox.anicoubs.data.db.entity.vk.wall

import com.google.gson.annotations.SerializedName

data class Reposts(
    val count: Int,
    @SerializedName("user_reposted")
    val userReposted: Int
)