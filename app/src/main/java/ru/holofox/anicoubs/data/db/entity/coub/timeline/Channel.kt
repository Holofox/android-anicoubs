package ru.holofox.anicoubs.data.db.entity.coub.timeline

import com.google.gson.annotations.SerializedName

data class Channel(
    val id: Int,
    val permalink: String,
    val title: String,
    val description: String?,
    @SerializedName("followers_count")
    val followersCount: Int,
    @SerializedName("following_count")
    val followingCount: Int,
    @SerializedName("avatar_versions")
    val avatarVersions: Versions
)