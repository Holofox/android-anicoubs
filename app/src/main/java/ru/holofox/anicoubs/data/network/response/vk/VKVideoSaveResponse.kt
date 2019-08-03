package ru.holofox.anicoubs.data.network.response.vk

import com.google.gson.annotations.SerializedName

data class VKVideoSaveResponse(
    @SerializedName("upload_url")
    val uploadUrl: String,
    val vid: Int,
    @SerializedName("owner_id")
    val ownerId: Int,
    val name: String,
    val description: String,
    @SerializedName("access_key")
    val accessKey: String
)