package ru.holofox.anicoubs.data.db.entity.coub.channel

import com.google.gson.annotations.SerializedName

data class Authentications(
    val id: Int,
    @SerializedName("channel_id")
    val channelId: Int,
    val provider: String,
    @SerializedName("username_from_provider")
    val usernameFromProvider: String
)