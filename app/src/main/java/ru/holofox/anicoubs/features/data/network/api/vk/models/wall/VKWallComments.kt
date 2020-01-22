package ru.holofox.anicoubs.features.data.network.api.vk.models.wall

import com.google.gson.annotations.SerializedName

data class VKWallComments(
    @SerializedName("can_close")
    val canClose: Int,
    @SerializedName("can_post")
    val canPost: Int,
    val count: Int,
    @SerializedName("group_can_post")
    val groupsCanPost: Boolean
)